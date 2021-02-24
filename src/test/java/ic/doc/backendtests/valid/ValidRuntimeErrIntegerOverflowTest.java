package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidRuntimeErrIntegerOverflowTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/runtimeErr/integerOverflow/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Disabled
  @Tag("backend")
  @Tag("runtimeErr")
  @Tag("integerOverflow")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validIntegerOverflowTests(String testName) {
    backendTestFile(groupTestPath + testName);
  }
}
