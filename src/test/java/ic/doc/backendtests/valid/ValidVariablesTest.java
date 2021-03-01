package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidVariablesTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/variables/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("backend")
  @Tag("variables")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validVariablesTests(String testName) {
    backendTestFile(groupTestPath + testName);
  }
}
