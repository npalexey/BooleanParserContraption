package com.nikitiuk.booleanparsercontraption.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceStarter {
    /*public static void main(String[] args) {
        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("a", true);
        parseBoolExpr("a", variablesMap);
    }*/
    public static Boolean parseBoolExpr(String expr){
        Boolean result = Contraption.run(expr);
        if(result != null){
            return result;
        } else {
            System.out.println("Returned Null");
            return false;
        }
    }
    public static Boolean parseBoolExpr(String expr, Map valuesMap){
        Boolean result = Contraption.run(expr,valuesMap);
        if(result != null){
            return result;
        } else {
            System.out.println("Returned Null");
            return false;
        }
    }
}
