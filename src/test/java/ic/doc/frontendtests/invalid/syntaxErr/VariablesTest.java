package ic.doc.frontendtests.invalid.syntaxErr;

import ic.doc.WaccFrontend;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class VariablesTest {

  private static final Integer SYNTAX_ERROR_CODE = 200;
  private static final String basepath = "/invalid/syntaxErr/variables/";
  private static final Pattern pattern = Pattern.compile("Exit code (\\d+) returned.");

  private int getExitCodeFromFile(String filepath) throws IOException {

    // Read file into string
    Path path = Path.of(this.getClass().getResource(filepath).getPath());
    String content = new String(Files.readAllBytes(path));

    // Look for exit code in string
    Matcher matcher = pattern.matcher(content);
    if (!matcher.find()) {
      return 0;  // No exit code found, presume to be 0
    } else if (matcher.group(1).equals("200")) {
      return 200; // Semantic error
    } else if (matcher.group(1).equals("100")){
      return 100;
    } else {
      throw new IllegalStateException("Error code other than 100 and 200 found!");
    }
  }

  @Test
  public void badIntAssignmentReturnsExitCode() throws Exception {
    String filepath = "/refCompilerOutput" + basepath + "badintAssignments.waccast";

    InputStream inputStream = this.getClass().getResourceAsStream(basepath + "intDeclaration.wacc");
    String compilerResult = WaccFrontend.parseFromInputStream(inputStream);

    System.out.println(getExitCodeFromFile(filepath));
  }

}
