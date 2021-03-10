package ic.doc.backendtests.op;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

public class ConstantFoldingTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/optimization/constantPropogation/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("backend")
  @Tag("op")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validConstantFoldingTests(String testName) {
    backendTestFile(groupTestPath + testName);
  }
}
