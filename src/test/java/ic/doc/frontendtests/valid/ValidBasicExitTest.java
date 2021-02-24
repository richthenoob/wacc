package ic.doc.frontendtests.valid;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidBasicExitTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/basic/exit/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("basic")
  @Tag("exit")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validBasicExitTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
