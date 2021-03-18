package ic.doc.frontendtests.invalid.semanticErr;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSemanticImportsTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/invalid/semanticErr/imports/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("invalid")
  @Tag("semantic")
  @Tag("imports")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSemanticImportsTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
