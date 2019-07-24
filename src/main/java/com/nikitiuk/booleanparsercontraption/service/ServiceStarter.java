package com.nikitiuk.booleanparsercontraption.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class ServiceStarter {
    private static final Logger logger =  LoggerFactory.getLogger(ServiceStarter.class);
    /*public static void main(String[] args) {
        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("a", true);
        logger.info(parseBoolExpr("a != false", variablesMap));
    }*/
    public static Boolean parseBoolExpr(String expr){
        Boolean result = Contraption.run(expr);
        if(result != null){
            return result;
        } else {
            logger.info("Returned Null");
            return false;
        }
    }
    public static Boolean parseBoolExpr(String expr, Map valuesMap){
        Boolean result = Contraption.run(expr,valuesMap);
        if(result != null){
            return result;
        } else {
            logger.info("Returned Null");
            return false;
        }
    }
}
