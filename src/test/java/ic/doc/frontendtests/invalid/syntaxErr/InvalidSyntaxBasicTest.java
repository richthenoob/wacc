package ic.doc.frontendtests.invalid.syntaxErr;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSyntaxBasicTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/invalid/syntaxErr/basic/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("invalid")
  @Tag("syntax")
  @Tag("basic")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSyntaxBasicTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
