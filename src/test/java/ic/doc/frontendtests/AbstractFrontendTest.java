package ic.doc.frontendtests;

import static ic.doc.TestUtils.readFileIntoString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.stringContainsInOrder;

import ic.doc.TestUtils;
import ic.doc.frontend.WaccFrontend;
import ic.doc.frontend.errors.SemanticException;
import ic.doc.frontend.errors.SyntaxException;
import ic.doc.frontend.nodes.ProgNode;
import ic.doc.frontend.utils.fsUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractFrontendTest {

  private static final String REFERENCE_DIR = "/frontendReference";

  private static final Pattern exitCodePattern = Pattern.compile("Exit code (\\d+) returned.");
  private static final Pattern semanticErrorMessagePattern = Pattern.compile("Semantic Error at");
  private static final Pattern syntacticErrorMessagePattern = Pattern.compile("Syntactic Error at");

  private static final Integer SYNTAX_EXIT_CODE = WaccFrontend.SYNTAX_EXIT_CODE;
  private static final Integer SEMANTIC_EXIT_CODE = WaccFrontend.SEMANTIC_EXIT_CODE;

  private static int getExitCodeFromContent(String content) {
    /* Look for exit code in string */
    Matcher matcher = exitCodePattern.matcher(content);
    if (!matcher.find()) {
      return 0; /* No exit code found, presume to be 0 */
    } else if (matcher.group(1).equals(SEMANTIC_EXIT_CODE.toString())) {
      return SEMANTIC_EXIT_CODE;
    } else if (matcher.group(1).equals(SYNTAX_EXIT_CODE.toString())) {
      return SYNTAX_EXIT_CODE;
    } else {
      throw new IllegalStateException("Error code other than 100 and 200 found!");
    }
  }

  private static void getErrorMessagesFromContent(String content) {
    /* Look for exit code in string for semantic errors*/
    Matcher semanticMatcher = semanticErrorMessagePattern.matcher(content);

    /* Look for exit code in string for syntactic errors*/
    Matcher syntacticMatcher = syntacticErrorMessagePattern.matcher(content);

    if (semanticMatcher.find()) {
      System.out.println("\nError messages from file:");
      System.out.println(content.substring(semanticMatcher.start()));
    } else if (syntacticMatcher.find()) {
      System.out.println("\nError messages from file:");
      System.out.println(content.substring(syntacticMatcher.start()));
    } else {
      System.out.println("No error messages expected.");
    }
  }

  public ProgNode frontendTestFile(String testFilepath) {
    ProgNode rootNode;

    /* Path of file */
    String path = TestUtils.EXAMPLES_DIR + testFilepath;
    /* Run sample program through frontend */
    int frontendExitCode = TestUtils.SUCCESS_EXIT_CODE;
    InputStream inputStream = this.getClass().getResourceAsStream(path);

    String absolutePath = this.getClass().getResource(path).getPath();

    try {
      rootNode = fsUtils.parseRootFile(absolutePath, inputStream);
    } catch (SyntaxException e) {
      rootNode = null;
      frontendExitCode = SYNTAX_EXIT_CODE;
    } catch (SemanticException e) {
      rootNode = null;
      frontendExitCode = SEMANTIC_EXIT_CODE;
    } catch (IOException e) {
      throw new IllegalStateException("An error occurred while parsing input"
          + " stream in frontend test for filepath: " + testFilepath);
    }

    /* Get reference compiler's exit code and error messages. */
    String filepath = REFERENCE_DIR + testFilepath + "ast";
    String fileContent = readFileIntoString(filepath);

    int referenceExitCode = getExitCodeFromContent(fileContent);
    getErrorMessagesFromContent(fileContent);
    assertThat("Different error code", frontendExitCode, equalTo(referenceExitCode));

    return rootNode;
  }
}
