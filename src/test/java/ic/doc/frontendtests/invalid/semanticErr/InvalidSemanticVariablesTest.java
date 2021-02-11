package ic.doc.frontendtests.invalid.semanticErr;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSemanticVariablesTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/invalid/semanticErr/variables/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("invalid")
  @Tag("semantic")
  @Tag("variables")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSemanticVariablesTests(String testName) throws Exception {
    testFile(groupTestPath + testName);
  }
}
