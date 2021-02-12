package ic.doc.frontendtests;

import ic.doc.frontend.WaccFrontend;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class WaccFrontendTest {

  @Test
  public void frontendAcceptsCommandLineArguments() throws IOException {
    InputStream inputStream =
        this.getClass().getResourceAsStream("/wacc_examples/valid/variables/intDeclaration.wacc");
    System.err.println("\nError messages from compiler:");
    System.out.println(WaccFrontend.parse(inputStream));
  }
}
