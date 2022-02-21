package com.upday.exception;

/**
 * @author jalajchawla
 */
public class BadFormatException extends RuntimeException{
    private static final long serialVersionUID = 3749138303937568590L;

    public BadFormatException(String message) {
        super(message);
    }
}
