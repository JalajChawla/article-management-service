package com.upday.exception;

/**
 * @author jalajchawla
 */
public class ArticleAlreadyExistException extends RuntimeException{
    private static final long serialVersionUID = 3749138303937568590L;

    private static final String DEFAULT_MESSAGE = "Article Already exist.";

    public ArticleAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    public ArticleAlreadyExistException(String message) {
        super(message);
    }
}
