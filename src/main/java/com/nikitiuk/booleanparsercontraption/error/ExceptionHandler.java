package com.nikitiuk.booleanparsercontraption.error;

import com.nikitiuk.booleanparsercontraption.model.Token;

public class ExceptionHandler extends RuntimeException {
    private final Token token;

    public ExceptionHandler(Token token, String message) {
        super(message);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
