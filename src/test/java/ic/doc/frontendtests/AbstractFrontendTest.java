package ic.doc.frontendtests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import ic.doc.SyntaxException;
import ic.doc.WaccFrontend;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractFrontendTest {

  private static final Integer SUCCESS_EXIT_CODE = 0;
  private static final Integer SYNTAX_EXIT_CODE = 100;
  private static final Integer SEMANTIC_EXIT_CODE = 200;
  private static final Pattern exitCodePattern = Pattern.compile("Exit code (\\d+) returned.");
  private static final Pattern errorMessagePattern = Pattern.compile("Syntactic Error at");
  private static final Pattern endingMessagePattern = Pattern.compile("parser error(s)");

  private static final String WACC_FILE_EXTENSION = ".wacc";
  private static final String EXAMPLES_DIR = "/wacc_examples";
  private static final String REFERENCE_DIR = "/refCompilerOutput";

  private int getExitCodeFromFile(String filepath) throws IOException {
    // Read file into string
    Path path = Path.of(this.getClass().getResource(filepath).getPath());
    String content = new String(Files.readAllBytes(path));

    // Look for exit code in string
    Matcher matcher = exitCodePattern.matcher(content);
    if (!matcher.find()) {
      return 0;  // No exit code found, presume to be 0
    } else if (matcher.group(1).equals(SEMANTIC_EXIT_CODE.toString())) {
      return SEMANTIC_EXIT_CODE;
    } else if (matcher.group(1).equals(SYNTAX_EXIT_CODE.toString())) {
      return SYNTAX_EXIT_CODE;
    } else {
      throw new IllegalStateException("Error code other than 100 and 200 found!");
    }
  }

  private void getErrorMessagesFromFile(String filepath) throws IOException {
    // Read file into string
    Path path = Path.of(this.getClass().getResource(filepath).getPath());
    String content = new String(Files.readAllBytes(path));
    // Look for exit code in string
    Matcher matcher = errorMessagePattern.matcher(content);
    int prevStart = -1; // index of start of previous error message
    while (matcher.find()) {
      if (prevStart != -1) {
        System.out.println(content.substring(prevStart, matcher.start()));
      }
      prevStart = matcher.end(); // index of start of previous error message
    }
    Matcher endMatcher = endingMessagePattern.matcher(content);
    if (endMatcher.find()) {
      System.out.println(content.substring(prevStart, matcher.start() - 2));
    }
  }

  protected static Collection<String> getAllTestNames(String groupTestPath) {
    List<String> files;

    try {
      String testDirPath = AbstractFrontendTest.class.getResource(EXAMPLES_DIR + groupTestPath)
          .getPath();

      // Go through directory and find all .wacc files
      files = Files.walk(Path.of(testDirPath))
          .filter(name -> name.toString()
              .toLowerCase()
              .endsWith(WACC_FILE_EXTENSION))
          .map(Path::getFileName)
          .map(Path::toString)
          .collect(Collectors.toList());
    } catch (IOException e) {
      System.out.println(e.toString());
      files = null;
    }

    return files;
  }

  public void syntaxTest(String testFilepath) throws Exception {
    // Run sample program through frontend
    int frontendExitCode = SUCCESS_EXIT_CODE;
    InputStream inputStream = this.getClass()
        .getResourceAsStream(EXAMPLES_DIR + testFilepath);

    try {
      String compilerResult = WaccFrontend.parseFromInputStream(inputStream);
      // Do something with compilerResult
    } catch (SyntaxException e) {
//      System.out.println(e.getMessage());
      frontendExitCode = SYNTAX_EXIT_CODE;
    }
    // Add SemanticException here

    // Get reference compiler's exit code
    String filepath = REFERENCE_DIR + testFilepath + "ast";
    int referenceExitCode = getExitCodeFromFile(filepath);
    System.out.println("Error messages from file:");
    getErrorMessagesFromFile(filepath);
    assertThat("Different error code", frontendExitCode, equalTo(referenceExitCode));

  }

}
