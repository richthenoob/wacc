package ic.doc.frontendtests.invalid.syntaxErr;

import ic.doc.SyntaxException;
import ic.doc.WaccFrontend;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class InvalidVariablesTest {

  private static final Integer SYNTAX_ERROR_CODE = 100;
  private static final Integer SEMANTIC_ERROR_CODE = 200;
  private static final String testpath = "/invalid/syntaxErr/variables/";
  private static final Pattern pattern = Pattern.compile("Exit code (\\d+) returned.");

  private int getExitCodeFromFile(String filepath) throws IOException {

    // Read file into string
    Path path = Path.of(this.getClass().getResource(filepath).getPath());
    String content = new String(Files.readAllBytes(path));

    // Look for exit code in string
    Matcher matcher = pattern.matcher(content);
    if (!matcher.find()) {
      return 0;  // No exit code found, presume to be 0
    } else if (matcher.group(1).equals(SEMANTIC_ERROR_CODE.toString())) {
      return SEMANTIC_ERROR_CODE;
    } else if (matcher.group(1).equals(SYNTAX_ERROR_CODE.toString())){
      return SYNTAX_ERROR_CODE;
    } else {
      throw new IllegalStateException("Error code other than 100 and 200 found!");
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"badintAssignments", "badintAssignments1", "badintAssignments2", "bigIntAssignment", "varNoName"})
  public void badIntAssignmentReturnsExitCode(String testName) throws Exception {
    int errorCode = 0;
    String filepath = "/refCompilerOutput" + testpath + testName + ".waccast";

    InputStream inputStream = this.getClass().getResourceAsStream("/wacc_examples" + testpath + testName + ".wacc");

    try {
      String compilerResult = WaccFrontend.parseFromInputStream(inputStream);
      // Do something with compilerResult
    } catch (SyntaxException e) {
//      System.out.println(e.getMessage());
      errorCode = SYNTAX_ERROR_CODE;
    }
    // Add SemanticException here

    assertThat("Different error code", errorCode, equalTo(getExitCodeFromFile(filepath)));
  }

}
