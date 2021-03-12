package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidAdvancedTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/advanced/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("backend")
  @Tag("advanced")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validAdvancedTests(String testName) {
    backendTestFile(groupTestPath + testName);
  }
}
