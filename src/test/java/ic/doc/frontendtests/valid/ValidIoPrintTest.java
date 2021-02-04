package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidIoPrintTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/IO/print/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validIoPrintTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }

}
