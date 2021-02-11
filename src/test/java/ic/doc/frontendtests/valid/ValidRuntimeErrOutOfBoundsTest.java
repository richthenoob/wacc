package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidRuntimeErrOutOfBoundsTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/runtimeErr/arrayOutOfBounds/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("runtimeErr")
  @Tag("outOfBounds")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validArrayOutOfBoundsTests(String testName) throws Exception {
    testFile(groupTestPath + testName);
  }
}
