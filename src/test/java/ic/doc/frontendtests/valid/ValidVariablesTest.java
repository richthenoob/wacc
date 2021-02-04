package ic.doc.frontendtests.valid;

import java.util.Collection;
import ic.doc.frontendtests.AbstractFrontendTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidVariablesTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/valid/variables/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validVariablesTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }

}

