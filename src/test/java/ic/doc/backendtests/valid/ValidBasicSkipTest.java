package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidBasicSkipTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/basic/skip/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("backend")
  @Tag("basic")
  @Tag("skip")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validSkipTests(String testName) {
    backendTestFile(groupTestPath + testName);
  }
}
