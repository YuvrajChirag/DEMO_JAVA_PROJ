package scanner;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Converts raw source code into tokens.
 * <p>
 * Responsibilities:
 * - Normalize lines.
 * - Track indentation (4 spaces) with INDENT/DEDENT tokens.
 * - Produce lexical tokens for keywords, operators, identifiers, and literals.
 */
public class Tokenizer {
    private static final int INDENT_SIZE = 4;

    private final String source;

    public Tokenizer(String source) {
        this.source = source == null ? "" : source;
    }

    /**
     * Performs full lexical analysis on source text.
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Deque<Integer> indentLevels = new ArrayDeque<>();
        indentLevels.push(0);

        String normalizedSource = source.replace("\r\n", "\n").replace('\r', '\n');
        String[] lines = normalizedSource.split("\n", -1);

        for (int index = 0; index < lines.length; index++) {
            String line = lines[index];
            int lineNumber = index + 1;

            if (!line.trim().isEmpty()) {
                processIndentation(tokens, indentLevels, line, lineNumber);
                int currentIndent = countIndent(line, lineNumber);
                tokenizeLine(line.substring(currentIndent), lineNumber, tokens);
            }

            // Every source line explicitly ends with NEWLINE for simple parsing rules.
            tokens.add(new Token(TokenType.NEWLINE, "", lineNumber));
        }

        closeOpenIndentationBlocks(tokens, indentLevels, lines.length);
        tokens.add(new Token(TokenType.EOF, "", lines.length + 1));
        return tokens;
    }

    /**
     * Emits INDENT/DEDENT tokens based on current line indentation depth.
     */
    private void processIndentation(List<Token> tokens, Deque<Integer> indentLevels, String line, int lineNumber) {
        int indent = countIndent(line, lineNumber);

        while (indent > indentLevels.peek()) {
            int nextLevel = indentLevels.peek() + INDENT_SIZE;
            indentLevels.push(nextLevel);
            if (nextLevel > indent) {
                throw new RuntimeException("Invalid indentation at line " + lineNumber);
            }
            tokens.add(new Token(TokenType.INDENT, "", lineNumber));
        }

        while (indent < indentLevels.peek()) {
            indentLevels.pop();
            tokens.add(new Token(TokenType.DEDENT, "", lineNumber));
        }

        if (indent != indentLevels.peek()) {
            throw new RuntimeException("Invalid indentation at line " + lineNumber);
        }
    }

    /**
     * Flushes remaining indentation levels at end of file.
     */
    private void closeOpenIndentationBlocks(List<Token> tokens, Deque<Integer> indentLevels, int lineCount) {
        while (indentLevels.peek() > 0) {
            indentLevels.pop();
            tokens.add(new Token(TokenType.DEDENT, "", lineCount));
        }
    }

    /**
     * Counts leading spaces and validates indentation rules.
     */
    private int countIndent(String line, int lineNumber) {
        int indent = 0;
        while (indent < line.length() && line.charAt(indent) == ' ') {
            indent++;
        }
        if (indent < line.length() && line.charAt(indent) == '\t') {
            throw new RuntimeException("Tabs are not supported for indentation at line " + lineNumber);
        }
        if (indent % INDENT_SIZE != 0) {
            throw new RuntimeException("Indentation must use multiples of 4 spaces at line " + lineNumber);
        }
        return indent;
    }

    /**
     * Tokenizes a single non-empty source line.
     */
    private void tokenizeLine(String line, int lineNumber, List<Token> tokens) {
        int currentIndex = 0;
        while (currentIndex < line.length()) {
            char currentChar = line.charAt(currentIndex);

            if (Character.isWhitespace(currentChar)) {
                currentIndex++;
                continue;
            }

            if (Character.isDigit(currentChar)) {
                currentIndex = tokenizeNumber(line, lineNumber, tokens, currentIndex);
                continue;
            }

            if (Character.isLetter(currentChar) || currentChar == '_') {
                currentIndex = tokenizeIdentifier(line, lineNumber, tokens, currentIndex);
                continue;
            }

            if (currentChar == '"') {
                currentIndex = tokenizeString(line, lineNumber, tokens, currentIndex);
                continue;
            }

            int nextIndex = tokenizeMultiCharacterToken(line, lineNumber, tokens, currentIndex);
            if (nextIndex != currentIndex) {
                currentIndex = nextIndex;
                continue;
            }

            currentIndex = tokenizeSingleCharacterToken(tokens, lineNumber, currentChar, currentIndex);
        }
    }

    /**
     * Consumes a numeric literal starting at startIndex.
     */
    private int tokenizeNumber(String line, int lineNumber, List<Token> tokens, int startIndex) {
        int endIndex = startIndex;
        while (endIndex < line.length() && (Character.isDigit(line.charAt(endIndex)) || line.charAt(endIndex) == '.')) {
            endIndex++;
        }
        tokens.add(new Token(TokenType.NUMBER, line.substring(startIndex, endIndex), lineNumber));
        return endIndex;
    }

    /**
     * Consumes identifiers and keyword-like names.
     */
    private int tokenizeIdentifier(String line, int lineNumber, List<Token> tokens, int startIndex) {
        int endIndex = startIndex;
        while (endIndex < line.length()
                && (Character.isLetterOrDigit(line.charAt(endIndex)) || line.charAt(endIndex) == '_')) {
            endIndex++;
        }
        tokens.add(new Token(TokenType.IDENTIFIER, line.substring(startIndex, endIndex), lineNumber));
        return endIndex;
    }

    /**
     * Consumes a quoted string literal with basic escape handling.
     */
    private int tokenizeString(String line, int lineNumber, List<Token> tokens, int openingQuoteIndex) {
        int currentIndex = openingQuoteIndex + 1;
        StringBuilder value = new StringBuilder();

        while (currentIndex < line.length() && line.charAt(currentIndex) != '"') {
            char currentChar = line.charAt(currentIndex);
            if (currentChar == '\\' && currentIndex + 1 < line.length()) {
                currentIndex++;
                value.append(resolveEscapeCharacter(line.charAt(currentIndex)));
            } else {
                value.append(currentChar);
            }
            currentIndex++;
        }

        if (currentIndex >= line.length() || line.charAt(currentIndex) != '"') {
            throw new RuntimeException(
                    "Unterminated string at line " + lineNumber + " starting index " + openingQuoteIndex);
        }

        tokens.add(new Token(TokenType.STRING, value.toString(), lineNumber));
        return currentIndex + 1;
    }

    /**
     * Resolves supported escaped characters (\n, \t, or raw character fallback).
     */
    private char resolveEscapeCharacter(char escapedChar) {
        switch (escapedChar) {
            case 'n':
                return '\n';
            case 't':
                return '\t';
            default:
                return escapedChar;
        }
    }

    /**
     * Tries to tokenize 2-character operators and returns new index if matched.
     */
    private int tokenizeMultiCharacterToken(String line, int lineNumber, List<Token> tokens, int currentIndex) {
        if (matches(line, currentIndex, ":=")) {
            tokens.add(new Token(TokenType.ASSIGN, ":=", lineNumber));
            return currentIndex + 2;
        }
        if (matches(line, currentIndex, "=>")) {
            tokens.add(new Token(TokenType.ARROW, "=>", lineNumber));
            return currentIndex + 2;
        }
        if (matches(line, currentIndex, "==")) {
            tokens.add(new Token(TokenType.EQEQ, "==", lineNumber));
            return currentIndex + 2;
        }
        if (matches(line, currentIndex, ">>")) {
            tokens.add(new Token(TokenType.PRINT, ">>", lineNumber));
            return currentIndex + 2;
        }
        return currentIndex;
    }

    /**
     * Checks whether the current index starts with tokenText.
     */
    private boolean matches(String line, int index, String tokenText) {
        return index + tokenText.length() <= line.length()
                && line.substring(index, index + tokenText.length()).equals(tokenText);
    }

    /**
     * Tokenizes one-character operators and punctuations.
     */
    private int tokenizeSingleCharacterToken(List<Token> tokens, int lineNumber, char currentChar, int currentIndex) {
        switch (currentChar) {
            case '+':
                tokens.add(new Token(TokenType.PLUS, "+", lineNumber));
                return currentIndex + 1;
            case '-':
                tokens.add(new Token(TokenType.MINUS, "-", lineNumber));
                return currentIndex + 1;
            case '*':
                tokens.add(new Token(TokenType.STAR, "*", lineNumber));
                return currentIndex + 1;
            case '/':
                tokens.add(new Token(TokenType.SLASH, "/", lineNumber));
                return currentIndex + 1;
            case '>':
                tokens.add(new Token(TokenType.GT, ">", lineNumber));
                return currentIndex + 1;
            case '<':
                tokens.add(new Token(TokenType.LT, "<", lineNumber));
                return currentIndex + 1;
            case '?':
                tokens.add(new Token(TokenType.IF, "?", lineNumber));
                return currentIndex + 1;
            case '@':
                tokens.add(new Token(TokenType.REPEAT, "@", lineNumber));
                return currentIndex + 1;
            case '(':
                tokens.add(new Token(TokenType.LPAREN, "(", lineNumber));
                return currentIndex + 1;
            case ')':
                tokens.add(new Token(TokenType.RPAREN, ")", lineNumber));
                return currentIndex + 1;
            default:
                throw new RuntimeException("Unexpected character '" + currentChar + "' at line " + lineNumber);
        }
    }
}
