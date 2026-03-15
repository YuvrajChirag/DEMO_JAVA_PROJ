import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Instruction> parse() {
        List<Instruction> instructions = new ArrayList<>();
        while (!isAtEnd()) {
            skipNewlines();
            if (isAtEnd()) {
                break;
            }
            instructions.add(parseInstruction());
            skipNewlines();
        }
        return instructions;
    }

    private Instruction parseInstruction() {
        if (match(TokenType.IF)) {
            Expression condition = parseExpression();
            consume(TokenType.ARROW, "Expected '=>' after if condition.");
            consume(TokenType.NEWLINE, "Expected newline after if header.");
            consume(TokenType.INDENT, "Expected indented block after if.");
            List<Instruction> body = parseBlock();
            return new IfInstruction(condition, body);
        }

        if (match(TokenType.REPEAT)) {
            Expression countExpr = parseExpression();
            consume(TokenType.ARROW, "Expected '=>' after repeat count.");
            consume(TokenType.NEWLINE, "Expected newline after repeat header.");
            consume(TokenType.INDENT, "Expected indented block after repeat.");
            List<Instruction> body = parseBlock();
            return new RepeatInstruction(countExpr, body);
        }

        if (match(TokenType.PRINT)) {
            Expression expression = parseExpression();
            return new PrintInstruction(expression);
        }

        if (check(TokenType.IDENTIFIER) && checkNext(TokenType.ASSIGN)) {
            String name = consume(TokenType.IDENTIFIER, "Expected variable name.").getValue();
            consume(TokenType.ASSIGN, "Expected ':=' after variable name.");
            Expression expression = parseExpression();
            return new AssignInstruction(name, expression);
        }

        throw error(peek(), "Unknown instruction starting with token " + peek().getType());
    }

    private List<Instruction> parseBlock() {
        List<Instruction> body = new ArrayList<>();
        while (!check(TokenType.DEDENT) && !isAtEnd()) {
            skipNewlines();
            if (check(TokenType.DEDENT) || isAtEnd()) {
                break;
            }
            body.add(parseInstruction());
            skipNewlines();
        }
        consume(TokenType.DEDENT, "Expected end of indented block.");
        return body;
    }

    private Expression parseExpression() {
        return parseComparison();
    }

    private Expression parseComparison() {
        Expression expr = parseTermExpression();
        while (match(TokenType.GT, TokenType.LT, TokenType.EQEQ)) {
            Token operator = previous();
            Expression right = parseTermExpression();
            expr = new BinaryOpNode(expr, operator.getValue(), right);
        }
        return expr;
    }

    private Expression parseTermExpression() {
        Expression expr = parseFactor();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expression right = parseFactor();
            expr = new BinaryOpNode(expr, operator.getValue(), right);
        }
        return expr;
    }

    private Expression parseFactor() {
        Expression expr = parsePrimary();
        while (match(TokenType.STAR, TokenType.SLASH)) {
            Token operator = previous();
            Expression right = parsePrimary();
            expr = new BinaryOpNode(expr, operator.getValue(), right);
        }
        return expr;
    }

    private Expression parsePrimary() {
        if (match(TokenType.NUMBER)) {
            return new NumberNode(Double.parseDouble(previous().getValue()));
        }
        if (match(TokenType.STRING)) {
            return new StringNode(previous().getValue());
        }
        if (match(TokenType.IDENTIFIER)) {
            return new VariableNode(previous().getValue());
        }
        if (match(TokenType.LPAREN)) {
            Expression expression = parseExpression();
            consume(TokenType.RPAREN, "Expected ')' after expression.");
            return expression;
        }
        throw error(peek(), "Expected expression.");
    }

    private void skipNewlines() {
        while (match(TokenType.NEWLINE)) {
            // skip
        }
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) {
            return advance();
        }
        throw error(peek(), message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return type == TokenType.EOF;
        }
        return peek().getType() == type;
    }

    private boolean checkNext(TokenType type) {
        if (current + 1 >= tokens.size()) {
            return false;
        }
        return tokens.get(current + 1).getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private RuntimeException error(Token token, String message) {
        return new RuntimeException("Parse error at line " + token.getLine() + ": " + message);
    }
}
