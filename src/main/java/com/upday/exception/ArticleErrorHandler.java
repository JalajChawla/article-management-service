package com.upday.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * @author jalajchawla
 */
@RestControllerAdvice
@Slf4j
public class ArticleErrorHandler {

    @ExceptionHandler(ArticleNotFoundException.class)
    public  ResponseEntity<ErrorDetails> handleArticleNotFound(ArticleNotFoundException tnf ){
        log.info("ArticleErrorHandler.handleArticleNotFound()");
        ErrorDetails details=new ErrorDetails(LocalDateTime.now(),tnf.getMessage(),"404- Article Not Found");
        return new ResponseEntity<ErrorDetails>(details,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ErrorDetails> handleAllProblems(Exception e){
        log.info("ArticleErrorHandler.handleAllProblems()");
        ErrorDetails details=new ErrorDetails(LocalDateTime.now(),e.getMessage(),"Problem in execution");
        return new ResponseEntity<ErrorDetails>(details,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
