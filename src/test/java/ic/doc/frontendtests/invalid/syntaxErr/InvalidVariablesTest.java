package ic.doc.frontendtests.invalid.syntaxErr;

import java.util.Collection;
import ic.doc.frontendtests.AbstractFrontendTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidVariablesTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/invalid/syntaxErr/variables/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidVariablesTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }

}
