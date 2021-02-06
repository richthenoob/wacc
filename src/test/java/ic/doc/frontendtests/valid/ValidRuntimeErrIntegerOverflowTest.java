package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidRuntimeErrIntegerOverflowTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/runtimeErr/integerOverflow/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("runtimeErr")
  @Tag("integerOverflow")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validIntegerOverflowTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }
}
