package ic.doc.frontendtests.invalid.syntaxErr;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvalidSyntaxArrayTest extends AbstractFrontendTest {

  private static final String groupTestPath = "/invalid/syntaxErr/array/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("invalid")
  @Tag("syntax")
  @Tag("array")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void invalidSyntaxArrayTests(String testName) throws Exception {
    testFile(groupTestPath + testName);
  }

}
