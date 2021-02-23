package ic.doc.frontendtests.valid;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidScopeTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/scope/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("scope")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validScopeTests(String testName) throws Exception {
    frontendTestFile(groupTestPath + testName);
  }
}
