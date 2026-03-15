import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Tokenizer {
    private final String source;

    public Tokenizer(String source) {
        this.source = source == null ? "" : source;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Deque<Integer> indentLevels = new ArrayDeque<>();
        indentLevels.push(0);

        String normalized = source.replace("\r\n", "\n").replace('\r', '\n');
        String[] lines = normalized.split("\n", -1);

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int lineNumber = i + 1;
            boolean isBlank = line.trim().isEmpty();

            if (!isBlank) {
                int indent = countIndent(line, lineNumber);
                while (indent > indentLevels.peek()) {
                    indentLevels.push(indentLevels.peek() + 4);
                    if (indentLevels.peek() > indent) {
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

                tokenizeLine(line.substring(indent), lineNumber, tokens);
            }

            tokens.add(new Token(TokenType.NEWLINE, "", lineNumber));
        }

        while (indentLevels.peek() > 0) {
            indentLevels.pop();
            tokens.add(new Token(TokenType.DEDENT, "", lines.length));
        }

        tokens.add(new Token(TokenType.EOF, "", lines.length + 1));
        return tokens;
    }

    private int countIndent(String line, int lineNumber) {
        int indent = 0;
        while (indent < line.length() && line.charAt(indent) == ' ') {
            indent++;
        }
        if (indent < line.length() && line.charAt(indent) == '\t') {
            throw new RuntimeException("Tabs are not supported for indentation at line " + lineNumber);
        }
        if (indent % 4 != 0) {
            throw new RuntimeException("Indentation must use multiples of 4 spaces at line " + lineNumber);
        }
        return indent;
    }

    private void tokenizeLine(String line, int lineNumber, List<Token> tokens) {
        int i = 0;
        while (i < line.length()) {
            char c = line.charAt(i);

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (Character.isDigit(c)) {
                int start = i;
                while (i < line.length() && (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.')) {
                    i++;
                }
                tokens.add(new Token(TokenType.NUMBER, line.substring(start, i), lineNumber));
                continue;
            }

            if (Character.isLetter(c) || c == '_') {
                int start = i;
                while (i < line.length() && (Character.isLetterOrDigit(line.charAt(i)) || line.charAt(i) == '_')) {
                    i++;
                }
                tokens.add(new Token(TokenType.IDENTIFIER, line.substring(start, i), lineNumber));
                continue;
            }

            if (c == '"') {
                int start = ++i;
                StringBuilder sb = new StringBuilder();
                while (i < line.length() && line.charAt(i) != '"') {
                    char ch = line.charAt(i);
                    if (ch == '\\' && i + 1 < line.length()) {
                        i++;
                        char escaped = line.charAt(i);
                        if (escaped == 'n') {
                            sb.append('\n');
                        } else if (escaped == 't') {
                            sb.append('\t');
                        } else {
                            sb.append(escaped);
                        }
                    } else {
                        sb.append(ch);
                    }
                    i++;
                }
                if (i >= line.length() || line.charAt(i) != '"') {
                    throw new RuntimeException("Unterminated string at line " + lineNumber + " starting index " + start);
                }
                i++;
                tokens.add(new Token(TokenType.STRING, sb.toString(), lineNumber));
                continue;
            }

            if (c == ':' && i + 1 < line.length() && line.charAt(i + 1) == '=') {
                tokens.add(new Token(TokenType.ASSIGN, ":=", lineNumber));
                i += 2;
                continue;
            }
            if (c == '=' && i + 1 < line.length() && line.charAt(i + 1) == '>') {
                tokens.add(new Token(TokenType.ARROW, "=>", lineNumber));
                i += 2;
                continue;
            }
            if (c == '=' && i + 1 < line.length() && line.charAt(i + 1) == '=') {
                tokens.add(new Token(TokenType.EQEQ, "==", lineNumber));
                i += 2;
                continue;
            }
            if (c == '>' && i + 1 < line.length() && line.charAt(i + 1) == '>') {
                tokens.add(new Token(TokenType.PRINT, ">>", lineNumber));
                i += 2;
                continue;
            }

            switch (c) {
                case '+':
                    tokens.add(new Token(TokenType.PLUS, "+", lineNumber));
                    i++;
                    break;
                case '-':
                    tokens.add(new Token(TokenType.MINUS, "-", lineNumber));
                    i++;
                    break;
                case '*':
                    tokens.add(new Token(TokenType.STAR, "*", lineNumber));
                    i++;
                    break;
                case '/':
                    tokens.add(new Token(TokenType.SLASH, "/", lineNumber));
                    i++;
                    break;
                case '>':
                    tokens.add(new Token(TokenType.GT, ">", lineNumber));
                    i++;
                    break;
                case '<':
                    tokens.add(new Token(TokenType.LT, "<", lineNumber));
                    i++;
                    break;
                case '?':
                    tokens.add(new Token(TokenType.IF, "?", lineNumber));
                    i++;
                    break;
                case '@':
                    tokens.add(new Token(TokenType.REPEAT, "@", lineNumber));
                    i++;
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LPAREN, "(", lineNumber));
                    i++;
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPAREN, ")", lineNumber));
                    i++;
                    break;
                default:
                    throw new RuntimeException("Unexpected character '" + c + "' at line " + lineNumber);
            }
        }
    }
}
