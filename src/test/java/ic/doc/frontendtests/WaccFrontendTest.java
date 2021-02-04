package ic.doc.frontendtests;

import ic.doc.WaccFrontend;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class WaccFrontendTest {

    @Test
    public void frontendAcceptsCommandLineArguments() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/wacc_examples/valid/variables/intDeclaration.wacc");
        System.out.println(WaccFrontend.parseFromInputStream(inputStream));
    }
}
