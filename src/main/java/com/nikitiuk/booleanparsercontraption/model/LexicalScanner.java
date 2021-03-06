package com.nikitiuk.booleanparsercontraption.model;

import com.nikitiuk.booleanparsercontraption.service.Contraption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexicalScanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private final Map<String, Object> inputVariables;
    private int start = 0;
    private int current = 0;
    private int line = 1;

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("false",  TokenType.FALSE);
        keywords.put("null",   TokenType.NULL);
        keywords.put("true",   TokenType.TRUE);
    }

    public LexicalScanner(String source, Map inputVariables) {
        this.source = source;
        this.inputVariables = inputVariables;
    }

    public LexicalScanner(String source) {
        this.source = source;
        this.inputVariables = new HashMap<>();
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '-': addToken(TokenType.MINUS); break;
            case '+': addToken(TokenType.PLUS); break;
            case '*': addToken(TokenType.STAR); break;
            case '/': addToken(TokenType.SLASH); break;
            case '!': addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG); break;
            case '<': addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS); break;
            case '>': addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER); break;
            case '=':
                if(match('=')){
                    addToken(TokenType.EQUAL_EQUAL);
                } else {
                    Contraption.error(line, "Unexpected character.");
                }
                break;
            case '&':
                if(match('&')){
                    addToken(TokenType.AND);
                } else {
                    Contraption.error(line, "Unexpected character.");
                }
                break;
            case '|':
                if(match('|')){
                    addToken(TokenType.OR);
                } else {
                    Contraption.error(line, "Unexpected character.");
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                break;

            case '"': string(); break;

            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Contraption.error(line, "Unexpected character.");
                }
                break;
        }
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);

        TokenType type = keywords.get(text);
        if (type == null) {
            if (inputVariables.containsKey(text) && inputVariables.get(text) instanceof Number) {
                addToken(TokenType.NUMBER,
                        Double.parseDouble(inputVariables.get(text).toString()));
            } else if (inputVariables.containsKey(text) && inputVariables.get(text) instanceof Boolean) {
                type = keywords.get(inputVariables.get(text).toString());
                addToken(type);
            } else {
                type = TokenType.IDENTIFIER;
                addToken(type);
            }
        } else {
            addToken(type);
        }
    }

    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(TokenType.NUMBER,
                Double.parseDouble(source.substring(start, current)));
    }


    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n'){
                line++;
            }
            advance();
        }

        if (isAtEnd()) {
            Contraption.error(line, "Unterminated string.");
            return;
        }

        advance();

        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.STRING, value);
    }


    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
