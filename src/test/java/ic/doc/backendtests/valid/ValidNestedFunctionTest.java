package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidNestedFunctionTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/function/nested_functions/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Disabled
  @Tag("backend")
  @Tag("function")
  @Tag("nested")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validNestedFunctionTests(String testName) {

    if (testName.equals("printInputTriangle.wacc")) {
      backendTestFileWithInput(groupTestPath + testName, "10");
      return;
    }

    if (testName.equals("fibonacciFullRec.wacc")) {
      backendTestFileWithInput(groupTestPath + testName, "30");
      return;
    }

    backendTestFile(groupTestPath + testName);
  }
}
