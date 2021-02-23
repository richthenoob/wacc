package ic.doc.frontendtests.valid;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidIoTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/valid/IO/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("io")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validIoPrintTests(String testName) throws Exception {
    frontendTestFile(groupTestPath + testName);
  }
}
