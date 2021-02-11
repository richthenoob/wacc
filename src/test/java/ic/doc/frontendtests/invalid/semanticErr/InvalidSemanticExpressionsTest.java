package ic.doc.frontendtests.invalid.semanticErr;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSemanticExpressionsTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/invalid/semanticErr/expressions/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

//  @Disabled("Disabled until visitors properly implemented. ")
  @Tag("invalid")
  @Tag("semantic")
  @Tag("expressions")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSemanticExpressionTests(String testName) throws Exception {
    testFile(groupTestPath + testName);
  }
}
