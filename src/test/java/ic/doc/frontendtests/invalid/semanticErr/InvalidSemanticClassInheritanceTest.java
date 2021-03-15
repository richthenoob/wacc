package ic.doc.frontendtests.invalid.semanticErr;

import ic.doc.TestUtils;
import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSemanticClassInheritanceTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/invalid/semanticErr/class/inheritance/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  //@Disabled
  @Tag("invalid")
  @Tag("semantic")
  @Tag("class")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSemanticClassInheritanceTests(String testName) {
    frontendTestFile(groupTestPath + testName);
  }
}
