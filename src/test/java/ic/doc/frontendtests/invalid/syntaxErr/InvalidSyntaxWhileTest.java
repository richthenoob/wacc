package ic.doc.frontendtests.invalid.syntaxErr;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSyntaxWhileTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/invalid/syntaxErr/while/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("invalid")
  @Tag("syntax")
  @Tag("while")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSyntaxWhileTests(String testName) throws Exception {
    frontendTestFile(groupTestPath + testName);
  }
}
