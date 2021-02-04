package ic.doc.frontendtests.valid;

import ic.doc.WaccFrontend;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;

public class ValidVariablesTest {

  private static final String basepath = "/wacc_examples/valid/variables/";

  @Test
  public void intDeclarationReturnsValidExitCode() throws IOException {
    InputStream inputStream = this.getClass().getResourceAsStream(basepath + "intDeclaration.wacc");
    String compilerResult = WaccFrontend.parseFromInputStream(inputStream);
    // todo: compare compilerResult to reference result
  }
}

