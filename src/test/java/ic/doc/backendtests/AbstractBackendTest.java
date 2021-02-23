package ic.doc.backendtests;

import ic.doc.backend.WaccBackend;
import ic.doc.frontend.nodes.ProgNode;
import ic.doc.frontendtests.AbstractFrontendTest;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback.Adapter;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DockerClientBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;

public class AbstractBackendTest {

  private static final String CONTAINER_NAME = System.getProperty("containerName");
  private static final String TEMP_ASSEMBLY_FILENAME = "temp.s";
  private static final String TEMP_EXEC_NAME = "execFile";
  private DockerClient dockerClient;

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

  @Test
  public void someTest() {

    dockerClient = DockerClientBuilder.getInstance().build();
    StringBuilder outputMessages = new StringBuilder();

    int crossCompileExitCode = compileAssemblyFile("/compile/" + TEMP_EXEC_NAME,
        "/compile/src/" + TEMP_ASSEMBLY_FILENAME);

    int emulateExitCode = emulateExecutable("/compile/" + TEMP_EXEC_NAME,
        outputMessages);

    System.out.println(crossCompileExitCode);
    System.out.println(emulateExitCode);
    System.out.println(outputMessages.toString());
  }

  public void backendTestFile(String testFilepath) throws IOException {
    /* Run test file through frontend to get root node of AST tree. */
    AbstractFrontendTest frontendTest = new AbstractFrontendTest(){};
    ProgNode rootNode = frontendTest.frontendTestFile(testFilepath);

    // TODO: check if rootnode is null

    /* Generate code and write to temporary file. */
    String code = WaccBackend.generateCode(rootNode);
    File tempDir = new File(System.getProperty("tempDirPath"));
    File assemblyFile = new File(tempDir, TEMP_ASSEMBLY_FILENAME);
    FileUtils.writeStringToFile(assemblyFile, code, (String) null);

    someTest();
  }

  @Test
  public void testHere() throws IOException {
    var v = new AbstractBackendTest();
    v.backendTestFile("/valid/basic/skip/skip.wacc");
  }

  private void bla() {
    List<Container> containers = dockerClient.listContainersCmd()
        .withNameFilter(Collections.singleton(CONTAINER_NAME))
        .exec();
    System.out.println(containers.get(0));
  }
}
