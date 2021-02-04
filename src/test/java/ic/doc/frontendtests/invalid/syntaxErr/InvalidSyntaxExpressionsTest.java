package ic.doc.frontendtests.invalid.syntaxErr;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSyntaxExpressionsTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/invalid/syntaxErr/expressions/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSyntaxExpressionTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }
}
