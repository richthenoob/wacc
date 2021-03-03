package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidIoTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/IO/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Disabled("Multiple reads from stdin not yet supported on backend testing.")
  @Tag("backend")
  @Tag("io")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validIoPrintTests(String testName) {
    backendTestFile(groupTestPath + testName);
  }
}
