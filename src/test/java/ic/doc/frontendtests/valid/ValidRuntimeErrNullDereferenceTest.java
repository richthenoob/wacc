package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidRuntimeErrNullDereferenceTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/runtimeErr/nullDereference/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("valid")
  @Tag("runtimeErr")
  @Tag("nullDereference")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validNullDereferenceTests(String testName) throws Exception {
    syntaxTest(groupTestPath + testName);
  }

}
