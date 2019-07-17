package com.nikitiuk.booleanparsercontraption.service;

import com.nikitiuk.booleanparsercontraption.error.ExceptionHandler;
import com.nikitiuk.booleanparsercontraption.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contraption {
    private static final Interpreter interpreter = new Interpreter();
    private static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("a", true);
        variablesMap.put("b", 3);
        run("3 < 2 == false", variablesMap);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            //run(reader.readLine());
            hadError = false;
        }
    }

    private static void run(String source, Map varSource) {
        LexicalScanner lexicalScanner = new LexicalScanner(source, varSource);
        List<Token> tokens = lexicalScanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        // Stop if there was a syntax error.
        if (hadError) return;

        interpreter.interpret(expression);
    }

    private static void run(String source) {
        LexicalScanner lexicalScanner = new LexicalScanner(source);
        List<Token> tokens = lexicalScanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        // Stop if there was a syntax error.
        if (hadError) return;

        interpreter.interpret(expression);
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    public static void error(Token token, String message) {
        if(token != null){
            if (token.getType() == TokenType.EOF) {
                report(token.getLine(), " at end", message);
            } else {
                report(token.getLine(), " at '" + token.getLexeme() + "'", message);
            }
        }
    }

    public static void runtimeError(ExceptionHandler error) {
        System.err.println(error.getMessage() +
                "\n[line " + error.getToken().getLine() + "]");
    }
}