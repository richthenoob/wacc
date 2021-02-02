package ic.doc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WaccFrontendTest {

    @Test
    public void frontendAcceptsCommandLineArguments() throws IOException {
        Path path = Paths.get("src/test/resources/wacc_examples/invalid/syntaxErr/variables/badintAssignments.wacc");
        String read = String.join("\n", Files.readAllLines(path));

        System.out.println(WaccFrontend.parse(read));
    }
}
