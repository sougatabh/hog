package com.sougata.exception;

/**
 * Created by sougata on 8/19/14.
 */
public class DBException extends RuntimeException {
    public  DBException(){
        super();
    }

    public DBException(Exception exception){
        super(exception);
    }

    public DBException(String message){
        super(message);
    }
}
