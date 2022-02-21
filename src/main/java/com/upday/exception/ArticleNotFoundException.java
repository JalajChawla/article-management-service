package com.upday.exception;

/**
 * @author jalajchawla
 */
public class ArticleNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public ArticleNotFoundException(String msg) {
        super(msg);
    }
}
