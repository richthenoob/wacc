package ic.doc.frontendtests.valid;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidClassInheritanceTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/class/inheritance/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("class")
  @Tag("inheritance")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validClassInheritanceTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
