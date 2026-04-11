import interpreter.Interpreter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Command-line entry point for the calculator interpreter.
 */
public class Main {
    /**
     * Reads a single .calc file and executes it.
     *
     * @param args command-line arguments, expected: <file.calc>
     * @throws IOException when file reading fails
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java Main <file.calc>");
            System.exit(1);
        }

        String sourceCode = Files.readString(Path.of(args[0]));
        new Interpreter().run(sourceCode);
    }
}
