package ic.doc.frontendtests.valid;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidArrayTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/valid/array/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("array")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validArrayTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
