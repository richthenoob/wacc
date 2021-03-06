package ic.doc;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

  public static final String WACC_FILE_EXTENSION = ".wacc";
  public static final String EXAMPLES_DIR = "/wacc_examples";
  public static final Integer SUCCESS_EXIT_CODE = 0;

  public static Collection<String> getAllTestNames(String groupTestPath) {
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

  public static String readFileIntoString(String filepath) {
    /* Read file into string */
    String content;
    Path path;
    try {
      path = Path.of(TestUtils.class.getResource(filepath).getPath());
      content = new String(Files.readAllBytes(path));
    } catch (IOException | NullPointerException e) {
      throw new IllegalStateException("An error occurred while parsing"
          + " file contents of file: " + filepath + ". Paths passed to "
          + "this function should be relative to the resources path.");
    }

    return content;
  }

}
