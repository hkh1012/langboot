package com.hkh.common.common.exception;

public class Request404Exception extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public Request404Exception(String message) {
        super(message);
    }
}
