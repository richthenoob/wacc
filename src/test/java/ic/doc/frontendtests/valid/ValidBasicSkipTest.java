package ic.doc.frontendtests.valid;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidBasicSkipTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/basic/skip/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("basic")
  @Tag("skip")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validSkipTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
