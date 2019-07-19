package com.nikitiuk.booleanparsercontraption.service;

import com.nikitiuk.booleanparsercontraption.error.ExceptionHandler;
import com.nikitiuk.booleanparsercontraption.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contraption {
    private static final Interpreter interpreter = new Interpreter();
    private static boolean hadError = false;
    private static Logger logger =  LogManager.getLogger(Contraption.class);

    /*public static void main(String[] args) throws IOException {
        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("a", false);
        run("a", variablesMap);
    }*/

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            //run(reader.readLine());
            hadError = false;
        }
    }

    protected static Boolean run(String source, Map varSource) {
        LexicalScanner lexicalScanner = new LexicalScanner(source, varSource);
        List<Token> tokens = lexicalScanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        // Stop if there was a syntax error.
        if (hadError) {
            logger.info("Syntax error");
            return null;
        }

        return Boolean.parseBoolean(interpreter.interpret(expression));
    }

    protected static Boolean run(String source) {
        LexicalScanner lexicalScanner = new LexicalScanner(source);
        List<Token> tokens = lexicalScanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        // Stop if there was a syntax error.
        if (hadError) {
            logger.info("Syntax error");
            return null;
        }

        return Boolean.parseBoolean(interpreter.interpret(expression));
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        logger.info(
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
        logger.info(error.getMessage());// +
                //"\n[line " + error.getToken().getLine() + "]");
    }
}