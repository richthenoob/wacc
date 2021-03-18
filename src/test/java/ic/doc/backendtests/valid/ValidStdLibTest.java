package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidStdLibTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/stdlib/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("backend")
  @Tag("stdlib")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validStdLibTest(String testName) {
    backendTestFile(groupTestPath + testName);
  }
}
