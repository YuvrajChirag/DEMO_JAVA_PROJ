package parser;

import evaluator.AssignInstruction;
import evaluator.IfInstruction;
import evaluator.Instruction;
import evaluator.PrintInstruction;
import evaluator.RepeatInstruction;
import scanner.Token;
import scanner.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursive-descent parser that transforms tokens into executable instructions.
 */
public class Parser {
    private final List<Token> tokens;
    private int currentIndex = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Parses the full token stream into a top-level instruction list.
     */
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

    /**
     * Parses one instruction based on the current leading token.
     */
    private Instruction parseInstruction() {
        if (match(TokenType.IF)) {
            return parseIfInstruction();
        }
        if (match(TokenType.REPEAT)) {
            return parseRepeatInstruction();
        }
        if (match(TokenType.PRINT)) {
            return new PrintInstruction(parseExpression());
        }
        if (check(TokenType.IDENTIFIER) && checkNext(TokenType.ASSIGN)) {
            return parseAssignmentInstruction();
        }
        throw error(peek(), "Unknown instruction starting with token " + peek().getType());
    }

    /**
     * Parses an if-block instruction.
     */
    private Instruction parseIfInstruction() {
        Expression<Boolean> condition = parseBooleanExpression();
        consume(TokenType.ARROW, "Expected '=>' after if condition.");
        consume(TokenType.NEWLINE, "Expected newline after if header.");
        consume(TokenType.INDENT, "Expected indented block after if.");
        return new IfInstruction(condition, parseBlock());
    }

    /**
     * Parses a repeat-block instruction.
     */
    private Instruction parseRepeatInstruction() {
        Expression<Object> countExpression = parseExpression();
        consume(TokenType.ARROW, "Expected '=>' after repeat count.");
        consume(TokenType.NEWLINE, "Expected newline after repeat header.");
        consume(TokenType.INDENT, "Expected indented block after repeat.");
        return new RepeatInstruction(countExpression, parseBlock());
    }

    /**
     * Parses a variable assignment instruction.
     */
    private Instruction parseAssignmentInstruction() {
        String variableName = consume(TokenType.IDENTIFIER, "Expected variable name.").getValue();
        consume(TokenType.ASSIGN, "Expected ':=' after variable name.");
        return new AssignInstruction(variableName, parseExpression());
    }

    /**
     * Parses an indented block until DEDENT.
     */
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

    /**
     * Parses a generic expression entry point.
     */
    private Expression<Object> parseExpression() {
        return parseComparison();
    }

    /**
     * Parses and validates a boolean condition expression.
     */
    private Expression<Boolean> parseBooleanExpression() {
        Expression<Object> expression = parseComparison();
        return env -> {
            Object value = expression.evaluate(env);
            if (value instanceof Boolean) {
                return (Boolean) value;
            }
            throw new IllegalArgumentException("If condition must be Boolean but got: " + value);
        };
    }

    /**
     * Parses comparison operators with lower precedence than arithmetic.
     */
    private Expression<Object> parseComparison() {
        Expression<Object> expression = parseTermExpression();
        while (match(TokenType.GT, TokenType.LT, TokenType.EQEQ)) {
            Token operator = previous();
            Expression<Object> right = parseTermExpression();
            expression = new BinaryOpNode(expression, operator.getValue(), right);
        }
        return expression;
    }

    /**
     * Parses additive operators (+ and -).
     */
    private Expression<Object> parseTermExpression() {
        Expression<Object> expression = parseFactor();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expression<Object> right = parseFactor();
            expression = new BinaryOpNode(expression, operator.getValue(), right);
        }
        return expression;
    }

    /**
     * Parses multiplicative operators (* and /).
     */
    private Expression<Object> parseFactor() {
        Expression<Object> expression = parsePrimary();
        while (match(TokenType.STAR, TokenType.SLASH)) {
            Token operator = previous();
            Expression<Object> right = parsePrimary();
            expression = new BinaryOpNode(expression, operator.getValue(), right);
        }
        return expression;
    }

    /**
     * Parses primitive values, variables, and parenthesized expressions.
     */
    private Expression<Object> parsePrimary() {
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
            Expression<Object> expression = parseExpression();
            consume(TokenType.RPAREN, "Expected ')' after expression.");
            return expression;
        }
        throw error(peek(), "Expected expression.");
    }

    /**
     * Consumes consecutive NEWLINE tokens.
     */
    private void skipNewlines() {
        while (match(TokenType.NEWLINE)) {
        }
    }

    /**
     * Advances if current token matches any provided type.
     */
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    /**
     * Consumes one token of expected type or throws a parse error.
     */
    private Token consume(TokenType expectedType, String message) {
        if (check(expectedType)) {
            return advance();
        }
        throw error(peek(), message);
    }

    /**
     * Checks if current token is of the provided type.
     */
    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return type == TokenType.EOF;
        }
        return peek().getType() == type;
    }

    /**
     * Looks one token ahead without consuming it.
     */
    private boolean checkNext(TokenType type) {
        if (currentIndex + 1 >= tokens.size()) {
            return false;
        }
        return tokens.get(currentIndex + 1).getType() == type;
    }

    /**
     * Moves parser cursor forward and returns consumed token.
     */
    private Token advance() {
        if (!isAtEnd()) {
            currentIndex++;
        }
        return previous();
    }

    /**
     * Returns true when parser reached EOF token.
     */
    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    /**
     * Returns current token without consuming it.
     */
    private Token peek() {
        return tokens.get(currentIndex);
    }

    /**
     * Returns most recently consumed token.
     */
    private Token previous() {
        return tokens.get(currentIndex - 1);
    }

    /**
     * Constructs consistent parse exceptions with line number details.
     */
    private RuntimeException error(Token token, String message) {
        return new IllegalArgumentException("Parse error at line " + token.getLine() + ": " + message);
    }
}
