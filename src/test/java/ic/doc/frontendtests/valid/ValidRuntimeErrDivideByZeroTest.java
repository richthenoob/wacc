package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidRuntimeErrDivideByZeroTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/runtimeErr/divideByZero/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("runtimeErr")
  @Tag("divideByZero")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validDivideByZeroTests(String testName) throws Exception {
    testFile(groupTestPath + testName);
  }
}
