package scanner;

/**
 * Single lexical unit produced by the tokenizer.
 */
public class Token {
    private final TokenType type;
    private final String value;
    private final int line;

    /**
     * @param type token category
     * @param value raw/decoded token text
     * @param line source line number
     */
    public Token(TokenType type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }

    /**
     * @return token type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * @return token value text
     */
    public String getValue() {
        return value;
    }

    /**
     * @return source line where token was read
     */
    public int getLine() {
        return line;
    }
}
