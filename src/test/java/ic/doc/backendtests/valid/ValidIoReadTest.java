package ic.doc.backendtests.valid;

import ic.doc.TestUtils;
import ic.doc.backendtests.AbstractBackendTest;
import java.util.Collection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
public class ValidIoReadTest extends AbstractBackendTest {

  private static final String groupTestPath = "/valid/IO/read/";

  private static Collection<String> getTestNames() {
    return TestUtils.getAllTestNames(groupTestPath);
  }

  @Tag("backend")
  @Tag("io")
  @Tag("read")
  @ParameterizedTest
  @MethodSource("getTestNames")
  public void validIoReadTests(String testName) {

    switch (testName) {
      case "echoBigInt.wacc":
        backendTestFileWithInput(groupTestPath + testName, "1000000000000");
        break;
      case "echoBigNegInt.wacc":
        backendTestFileWithInput(groupTestPath + testName, "-1000000000000");
        break;
      case "echoChar.wacc":
        backendTestFileWithInput(groupTestPath + testName, "a");
        break;
      case "echoInt.wacc":
        backendTestFileWithInput(groupTestPath + testName, "42");
        break;
      case "echoNegInt.wacc":
        backendTestFileWithInput(groupTestPath + testName, "-42");
        break;
      case "echoPuncChar.wacc":
        backendTestFileWithInput(groupTestPath + testName, ",");
        break;
      case "read.wacc":
        backendTestFileWithInput(groupTestPath + testName, "helloWorld");
        break;
      default:
        System.out.println("Invalid test file in validIoReadTests.");
    }

  }
}
