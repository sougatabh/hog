package com.sougata;

import com.sougata.exception.DBException;

import javax.sql.DataSource;

/**
 * Created by sougata on 8/19/14.
 */
public class DBHandlerFactory {

    public static DBHandler open(DataSource ds){
        if(ds == null){
            throw new DBException("Data Source is null");
        }
        return new DefaultDBHandler(ds);

    }



}
