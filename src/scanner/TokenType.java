package scanner;

/**
 * Token categories recognized by the lexer.
 */
public enum TokenType {
    /** Numeric literal such as 10 or 3.14. */
    NUMBER,
    /** Quoted string literal such as "hello". */
    STRING,
    /** Variable/function-style name made of letters, digits, and underscores. */
    IDENTIFIER,
    /** Addition operator (+). */
    PLUS,
    /** Subtraction operator (-). */
    MINUS,
    /** Multiplication operator (*). */
    STAR,
    /** Division operator (/). */
    SLASH,
    /** Assignment operator (:=). */
    ASSIGN,
    /** Print instruction operator (>>). */
    PRINT,
    /** If instruction marker (?). */
    IF,
    /** Repeat instruction marker (@). */
    REPEAT,
    /** Block arrow used in control headers (=>). */
    ARROW,
    /** Greater-than comparison operator (>). */
    GT,
    /** Less-than comparison operator (<). */
    LT,
    /** Equality comparison operator (==). */
    EQEQ,
    /** Left parenthesis (() used for grouping expressions. */
    LPAREN,
    /** Right parenthesis ()) used for grouping expressions. */
    RPAREN,
    /** End-of-line delimiter token. */
    NEWLINE,
    /** Entering a nested indentation block. */
    INDENT,
    /** Leaving a nested indentation block. */
    DEDENT,
    /** End-of-file sentinel token. */
    EOF
}
