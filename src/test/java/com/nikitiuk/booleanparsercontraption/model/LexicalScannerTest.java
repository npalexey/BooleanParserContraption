package com.nikitiuk.booleanparsercontraption.model;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LexicalScannerTest {

    private static Logger logger = null;

    @BeforeAll
    public static void setLogger() throws MalformedURLException
    {
        logger = LogManager.getLogger(LexicalScanner.class);
    }

    @Test
    public void scanTokensTest() {
        String testExpr = "55 - 234234.34";
        LexicalScanner lexicalScanner = new LexicalScanner(testExpr);
        List<Token> tokens = lexicalScanner.scanTokens();
        assertEquals(tokens.get(1).getType(), TokenType.MINUS);
        for (Token t:
             tokens) {
            logger.info(t.toString());
        }
        logger.info(tokens);
    }

    @Test
    public void scanTokensMapTest() {
        String testExpr = "a - 4 == 0 && bcd";
        Map<String,Object> variableMap = new HashMap<>();
        variableMap.put("a", 4);
        variableMap.put("bcd", true);
        LexicalScanner lexicalScanner = new LexicalScanner(testExpr, variableMap);
        List<Token> tokens = lexicalScanner.scanTokens();
        assertEquals(tokens.get(1).getType(), TokenType.MINUS);
        for (Token t:
                tokens) {
            logger.info(t.toString());
        }
        logger.info(tokens);
    }
}