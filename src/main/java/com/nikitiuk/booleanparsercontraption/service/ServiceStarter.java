package com.nikitiuk.booleanparsercontraption.service;

import java.util.Map;

public class ServiceStarter {
    public static Boolean parseBoolExpr(String expr){
        if(Contraption.run(expr) != null){
            return Contraption.run(expr);
        } else {
            System.out.println("Returned Null");
            return false;
        }
    }
    public static Boolean parseBoolExpr(String expr, Map<String, Double> valuesMap){
        if(Contraption.run(expr,valuesMap) != null){
            return Contraption.run(expr,valuesMap);
        } else {
            System.out.println("Returned Null");
            return false;
        }
    }
}
