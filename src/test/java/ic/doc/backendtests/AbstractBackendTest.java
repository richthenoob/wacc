package ic.doc.backendtests;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback.Adapter;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DockerClientBuilder;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;

public class AbstractBackendTest {

  public static final String CONTAINERNAME = "backendContainer";
  private DockerClient dockerClient;

  /* Assembles a .s file into an executable file. Returns exit code of
   * cross compilation. */
  private int compileAssemblyFile(String execFilePath, String sourcePath) {
    /* Craft command object. */
    ExecCreateCmdResponse execCreateCmdResponse = dockerClient
        .execCreateCmd(CONTAINERNAME)
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
          .exec(new Adapter<>())
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
  private int emulateExecutable(String execFilePath, StringBuilder stringOutput) {
    /* Craft command object. */
    ExecCreateCmdResponse execCreateCmdResponse = dockerClient
        .execCreateCmd(CONTAINERNAME)
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

  @Tag("backend")
  @Test
  public void someTest() {

    dockerClient = DockerClientBuilder.getInstance().build();
    String execFileName = "execFile";
    String sourceFile = "test.s";
    StringBuilder outputMessages = new StringBuilder();

    int crossCompileExitCode = compileAssemblyFile("/compile/" + execFileName,
        "/compile/src/" + sourceFile);

    int emulateExitCode = emulateExecutable("/compile/" + execFileName, outputMessages);

    System.out.println(crossCompileExitCode);
    System.out.println(emulateExitCode);
    System.out.println(outputMessages.toString());
  }

  private void bla() {
    List<Container> containers = dockerClient.listContainersCmd()
        .withNameFilter(Collections.singleton(CONTAINERNAME))
        .exec();
    System.out.println(containers.get(0));
  }
}
