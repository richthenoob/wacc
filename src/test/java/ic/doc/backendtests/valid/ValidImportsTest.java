package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidImportsTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/imports/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("backend")
  @Tag("imports")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validImportsTests(String testName) {
    backendTestFile(groupTestPath + testName);
  }
}
