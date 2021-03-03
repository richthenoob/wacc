package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidExpressionsTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/expressions/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

//  @Disabled
  @Tag("backend")
  @Tag("expressions")
  @ParameterizedTest
//  @MethodSource("getTestNames")
  @ValueSource(strings = {"negBothMod.wacc"})
  public void validExpressionsTests(String testName) {
    backendTestFile(groupTestPath + testName);
  }
}
