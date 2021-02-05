package ic.doc.frontendtests.invalid.syntaxErr;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSyntaxPrintTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/invalid/syntaxErr/print/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("invalid")
  @Tag("syntax")
  @Tag("print")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSyntaxPrintTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }
}
