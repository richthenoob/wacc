package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidExpressionsTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/expressions/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validExpressionsTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }

}
