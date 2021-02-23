package ic.doc.backendtests;

import static ic.doc.TestUtils.getAllTestNames;

import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidVariablesTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/variables/";

  private static Collection<String> getTestNames() {
    return getAllTestNames(groupTestPath);
  }

  @Tag("backend")
  @Tag("variables")
  @ParameterizedTest
  @ValueSource(strings = "intDeclaration.wacc")
  public void validVariablesTests(String testName) throws Exception {
    backendTestFile(groupTestPath + testName);
  }
}
