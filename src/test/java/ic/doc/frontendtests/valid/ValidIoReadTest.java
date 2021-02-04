package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidIoReadTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/IO/read/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validIoReadTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }

}
