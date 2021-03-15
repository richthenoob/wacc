package ic.doc.frontendtests.valid;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidClassTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/classes/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("class")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validClassTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
