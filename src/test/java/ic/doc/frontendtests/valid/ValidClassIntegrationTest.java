package ic.doc.frontendtests.valid;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidClassIntegrationTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/class/integration/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("class")
  @Tag("integration")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validClassIntegrationTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
