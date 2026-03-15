import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java Main <file.calc>");
            System.exit(1);
        }

        String sourceCode = Files.readString(Path.of(args[0]));
        Interpreter interpreter = new Interpreter();
        interpreter.run(sourceCode);
    }
}
