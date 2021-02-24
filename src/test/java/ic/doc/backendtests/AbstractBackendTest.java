package ic.doc.backendtests;

import static ic.doc.TestUtils.SUCCESS_EXIT_CODE;
import static ic.doc.TestUtils.readFileIntoString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback.Adapter;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.AccessMode;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import ic.doc.backend.WaccBackend;
import ic.doc.frontend.nodes.ProgNode;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractBackendTest {

  private DockerClient dockerClient;
  private static final String CONTAINER_NAME = System
      .getProperty("containerName");
  private static final String IMAGE_NAME = System
      .getProperty("imageName");
  private static final String TEMP_DIR_PATH = System
      .getProperty("tempDirPath");

  private static final String TEMP_ASSEMBLY_FILENAME = "temp.s";
  private static final String TEMP_EXEC_NAME = "execFile";
  private static final String REFERENCE_DIR = "/backendReference";

  /* Magic to extract relevant parts of the file. */
  private static final Pattern exitCodePattern = Pattern
      .compile("The exit code is (\\d+).");
  private static final Pattern stdoutPattern = Pattern.compile(
      "-- Executing\\.\\.\\.\n=+\n(.*)\n=", Pattern.DOTALL);

  private static int getExitCodeFromContent(String content) {
    /* Look for exit code in string */
    Matcher matcher = exitCodePattern.matcher(content);
    if (!matcher.find()) {
      throw new IllegalStateException(
          "No exit code found in file in backend test!");
    }
    return Integer.parseInt(matcher.group(1));
  }

  private static String getStdoutFromContent(String content) {
    /* Look for exit code in string */
    Matcher matcher = stdoutPattern.matcher(content);
    if (!matcher.find()) {
      return "";
    }
    return matcher.group(1);
  }

  /* Assembles a .s file into an executable file. Returns exit code of
   * cross compilation. */
  private int compileAssemblyFile(String execFilePath, String sourcePath) {
    /* Craft command object. */
    ExecCreateCmdResponse execCreateCmdResponse = dockerClient
        .execCreateCmd(CONTAINER_NAME)
        .withAttachStdout(true)
        .withAttachStderr(true)
        .withCmd("arm-linux-gnueabi-gcc",
            "-mcpu=arm1176jzf-s",
            "-mtune=arm1176jzf-s",
            "-o",
            execFilePath,
            sourcePath)
        .exec();

    /* Execute command on running docker container. */
    try {
      dockerClient.execStartCmd(execCreateCmdResponse.getId())
          .exec(new Adapter<>() {
            @Override
            public void onNext(Frame frame) {
              System.out.println(new String(frame.getPayload()));
            }
          })
          .awaitCompletion();
    } catch (InterruptedException e) {
      System.err.println(e.getMessage());
    }

    /* Extract exit code of command. */
    return dockerClient.inspectExecCmd(execCreateCmdResponse.getId())
        .exec()
        .getExitCodeLong()
        .intValue();
  }

  /* Runs the QEMU emulator on provided executable. Adds to string builder
   * any strings passed to stdout/stderr as a result of the emulation process.
   * Returns exit code. */
  private int emulateExecutable(String execFilePath,
      StringBuilder stringOutput) {
    /* Craft command object. */
    ExecCreateCmdResponse execCreateCmdResponse = dockerClient
        .execCreateCmd(CONTAINER_NAME)
        .withAttachStdout(true)
        .withAttachStderr(true)
        .withCmd("qemu-arm",
            "-L",
            "/usr/arm-linux-gnueabi/",
            execFilePath)
        .exec();

    /* Execute command on running docker container. */
    try {
      dockerClient.execStartCmd(execCreateCmdResponse.getId())
          .exec(new Adapter<>() {
            @Override
            public void onNext(Frame frame) {
              stringOutput.append(new String(frame.getPayload()));
            }
          })
          .awaitCompletion();
    } catch (InterruptedException e) {
      System.err.println(e.getMessage());
    }

    /* Extract exit code of command. */
    return dockerClient.inspectExecCmd(execCreateCmdResponse.getId())
        .exec()
        .getExitCodeLong()
        .intValue();
  }

  /* Start a docker container if not already started from gradle build.
   * Also creates a temporary folder to output .s files to. */
  private void setupTest() {
    dockerClient = DockerClientBuilder.getInstance().build();

    List<Container> containers = dockerClient.listContainersCmd()
        .withNameFilter(Collections.singleton(CONTAINER_NAME))
        .exec();

    if (containers.isEmpty()) {
      /* Equivalent to --rm -v tempDirPath:/compile/src:ro
       * Removes container when done and adds a volume. */
      HostConfig config = new HostConfig()
          .withBinds(new Bind(TEMP_DIR_PATH, new Volume("/compile/src"),
              AccessMode.ro))
          .withAutoRemove(true);

      CreateContainerResponse container = dockerClient
          .createContainerCmd(IMAGE_NAME)
          .withName(CONTAINER_NAME)
          .withTty(true)
          .withHostConfig(config)
          .exec();

      dockerClient.startContainerCmd(container.getId())
          .exec();
    }

    /* Create temp directory if it doesn't exist. */
    File directory = new File(TEMP_DIR_PATH);
    if (!directory.exists()) {
      if (directory.mkdir()) {
        /* Creating directory has failed. */
        throw new IllegalAccessError(
            "Failed to create directory in backend test.");
      }
    }
  }

  /* Checks exit code of cross compilation. */
  private void checkCrossCompilation() {
    int crossCompileExitCode = compileAssemblyFile("/compile/" + TEMP_EXEC_NAME,
        "/compile/src/" + TEMP_ASSEMBLY_FILENAME);

    assertThat("Cross-compilation failed.", crossCompileExitCode,
        equalTo(SUCCESS_EXIT_CODE));
  }

  /* Compares exit code and output of emulation to reference compiler. */
  private void checkEmulation(String filepath) {
    /* Run generated executable through emulator. */
    StringBuilder outputMessages = new StringBuilder();
    int emulateExitCode = emulateExecutable("/compile/" + TEMP_EXEC_NAME,
        outputMessages);
    String emulateOutputMessage = outputMessages.toString();

    /* Retrieve reference stdout and exit code. */
    String fileContent = readFileIntoString(filepath);
    String referenceOutputMessage = getStdoutFromContent(fileContent);
    int referenceExitCode = getExitCodeFromContent(fileContent);

    /* Print expected vs actual message output. */
    System.out.println("EXPECTED OUTPUT");
    System.out.println("===========================================================");
    System.out.println(referenceOutputMessage);
    System.out.println("===========================================================");
    System.out.println("ACTUAL OUTPUT");
    System.out.println("===========================================================");
    System.out.println(emulateOutputMessage);
    System.out.println("===========================================================");

    assertThat("Emulated exit code different from reference.",
        emulateExitCode,
        equalTo(referenceExitCode));

    assertThat("Emulated output message different from reference.",
        emulateOutputMessage,
        equalTo(referenceOutputMessage));
  }

  public void backendTestFile(String testFilepath) {

    /* Setup docker stuff and temporary folder if it doesn't exist. */
    setupTest();

    /* Run test file through frontend to get root node of AST tree. */
    AbstractFrontendTest frontendTest = new AbstractFrontendTest() {
    };
    ProgNode rootNode = frontendTest.frontendTestFile(testFilepath);

    if (rootNode == null) {
      throw new IllegalStateException("Null root node passed to backend test!");
    }

    /* Generate code and write to temporary file. */
    String code = WaccBackend.generateCode(rootNode);
    System.out.println(code);
    WaccBackend.writeToFile(TEMP_DIR_PATH + TEMP_ASSEMBLY_FILENAME, code);

    /* Cross compilation. */
    checkCrossCompilation();

    /* Emulation. */
    checkEmulation(REFERENCE_DIR + testFilepath + "asm");
  }
}
