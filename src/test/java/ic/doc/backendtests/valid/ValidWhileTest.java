package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidWhileTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/while/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("backend")
  @Tag("while")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validWhileTests(String testName) {

    if (testName.equals("rmStyleAddIO.wacc")) {
      /* WARNING: Manual test needed! */
      return;
    }

    if (testName.equals("fibonacciFullIt.wacc")) {
      backendTestFileWithInput(groupTestPath + testName, "12");
      return;
    }

    backendTestFile(groupTestPath + testName);
  }
}
