package ic.doc.frontendtests.valid;

import ic.doc.frontendtests.AbstractFrontendTest;
import java.util.Collection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ValidRuntimeErrTest extends AbstractFrontendTest {
  private static final String groupTestPath = "/valid/runtimeErr/";

  private static Collection<String> getArrayOutOfBoundsTestNames() {
    return getAllTestNames(groupTestPath + "arrayOutOfBounds/");
  }
  private static Collection<String> getDivideByZeroTestNames() {
    return getAllTestNames(groupTestPath + "divideByZero/");
  }
  private static Collection<String> getIntegerOverflowTestNames() {
    return getAllTestNames(groupTestPath + "integerOverflow/");
  }
  private static Collection<String> getNullDereferenceTestNames() {
    return getAllTestNames(groupTestPath + "nullDereference/");
  }

  @ParameterizedTest
  @MethodSource("getArrayOutOfBoundsTestNames")
  public void validArrayOutOfBoundsTests(String testName) throws Exception {
    syntaxTest(groupTestPath + "arrayOutOfBounds/" + testName);
  }

  @ParameterizedTest
  @MethodSource("getDivideByZeroTestNames")
  public void validDivideByZeroTests(String testName) throws Exception {
    syntaxTest(groupTestPath + "divideByZero/" + testName);
  }

  @ParameterizedTest
  @MethodSource("getIntegerOverflowTestNames")
  public void validIntegerOverflowTests(String testName) throws Exception {
    syntaxTest(groupTestPath + "integerOverflow/" + testName);
  }

  @ParameterizedTest
  @MethodSource("getNullDereferenceTestNames")
  public void validNullDereferenceTests(String testName) throws Exception {
    syntaxTest(groupTestPath + "nullDereference/" + testName);
  }

}
