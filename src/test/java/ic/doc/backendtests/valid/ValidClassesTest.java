package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidClassesTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/classes/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Disabled
  @Tag("backend")
  @Tag("class")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validIfTests(String testName) {
    if (testName.equals("classFibonacciRecursive.wacc")) {
      backendTestFileWithInput(groupTestPath + testName, "30");
      return;
    }
    backendTestFile(groupTestPath + testName);
  }
}
