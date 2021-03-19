package ic.doc.frontendtests.invalid.syntaxErr;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSyntaxClassInheritanceTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/invalid/syntaxErr/class/inheritance/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  //@Disabled
  @Tag("invalid")
  @Tag("syntax")
  @Tag("class")
  @Tag("inheritance")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSyntaxClassInheritanceTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
