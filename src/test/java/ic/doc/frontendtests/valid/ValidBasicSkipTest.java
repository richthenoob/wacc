package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidBasicSkipTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/basic/skip/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validSkipTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }

}
