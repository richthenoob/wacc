package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidSequenceTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/sequence/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("sequence")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validSequenceTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }
}
