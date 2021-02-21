package ic.doc.frontendtests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import ic.doc.frontend.WaccFrontend;
import ic.doc.frontend.errors.SemanticException;
import ic.doc.frontend.errors.SyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractFrontendTest {

  private static final Integer SUCCESS_EXIT_CODE = 0;
  private static final Pattern exitCodePattern = Pattern.compile("Exit code (\\d+) returned.");
  private static final Pattern semanticErrorMessagePattern = Pattern.compile("Semantic Error at");
  private static final Pattern syntacticErrorMessagePattern = Pattern.compile("Syntactic Error at");

  private static final String WACC_FILE_EXTENSION = ".wacc";
  private static final String EXAMPLES_DIR = "/wacc_examples";
  private static final String REFERENCE_DIR = "/frontendReference";

  private static final Integer SYNTAX_EXIT_CODE = WaccFrontend.SYNTAX_EXIT_CODE;
  private static final Integer SEMANTIC_EXIT_CODE = WaccFrontend.SEMANTIC_EXIT_CODE;

  private int getExitCodeFromFile(String filepath) throws IOException {
    /* Read file into string */
    Path path = Path.of(this.getClass().getResource(filepath).getPath());
    String content = new String(Files.readAllBytes(path));

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

  private void getErrorMessagesFromFile(String filepath) throws IOException {
    /* Read file into string */
    Path path = Path.of(this.getClass().getResource(filepath).getPath());
    String content = new String(Files.readAllBytes(path));

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

  protected static Collection<String> getAllTestNames(String groupTestPath) {
    List<String> files;

    try {
      /* Find resources path in test folder. */
      String testDirPath =
          AbstractFrontendTest.class.getResource(EXAMPLES_DIR + groupTestPath).getPath();

      /* Go through sub-directory and find all .wacc files, without
       * recursing in to sub-subdirectories. */
      files =
          Files.walk(Path.of(testDirPath), 1)
              .filter(name -> name.toString().toLowerCase().endsWith(WACC_FILE_EXTENSION))
              .map(Path::getFileName)
              .map(Path::toString)
              .collect(Collectors.toList());
    } catch (IOException e) {
      System.out.println(e.toString());
      files = null;
    }

    return files;
  }

  public void testFile(String testFilepath) throws Exception {
    System.out.println(testFilepath);
    /* Run sample program through frontend */
    int frontendExitCode = SUCCESS_EXIT_CODE;
    InputStream inputStream = this.getClass().getResourceAsStream(EXAMPLES_DIR + testFilepath);

    try {
      String compilerResult = WaccFrontend.parse(inputStream);
      // Do something with compilerResult
    } catch (SyntaxException e) {
      frontendExitCode = SYNTAX_EXIT_CODE;
    } catch (SemanticException e) {
      frontendExitCode = SEMANTIC_EXIT_CODE;
    }

    /* Get reference compiler's exit code */
    String filepath = REFERENCE_DIR + testFilepath + "ast";
    int referenceExitCode = getExitCodeFromFile(filepath);
    getErrorMessagesFromFile(filepath);
    assertThat("Different error code", frontendExitCode, equalTo(referenceExitCode));
  }
}
