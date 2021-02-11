package ic.doc.frontendtests.invalid.semanticErr;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSemanticIfTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/invalid/semanticErr/if/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("invalid")
  @Tag("semantic")
  @Tag("if")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSemanticIfTests(String testName) throws Exception {
    testFile(groupTestPath + testName);
  }
}
