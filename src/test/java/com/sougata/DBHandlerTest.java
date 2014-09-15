package com.sougata;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

/**
 * Unit test for simple App.
 */
public class DBHandlerTest
        extends TestCase {
    private DataSource ds;

    private static final DataSource createDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("");
        ds.setUrl("jdbc:mysql://localhost:3306/mydb");
        return ds;
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DBHandlerTest(String testName) {
        super(testName);
        ds = createDataSource();

    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DBHandlerTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testExecute() {

        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.execute("INSERT INTO emp(id,name,salary) values(4,'Manai',300)");

    }

    public void testExecuteWithParameter() {

        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.execute("INSERT INTO emp(id,name,salary) values(?,?,?)", new Integer(5), "Manai2", new Integer(340));

    }

    public void testRetrieve() {

        DBHandler _handler = DBHandlerFactory.open(ds);
        String jsonValue = _handler.retrieve("Select id,name,salary from emp");
        System.out.println(jsonValue);
        assertNotNull(jsonValue);

    }

    public void testRetrieveWithParameter() {

        DBHandler _handler = DBHandlerFactory.open(ds);
        String jsonValue = _handler.retrieve("Select * from emp where id=?", new Integer(100));
        System.out.println(jsonValue);
        assertNotNull(jsonValue);

    }

    public void testUpdateWithParameter() {
        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.execute("Update emp set dob=? where id=?", new java.util.Date(), new Integer(1));

    }

    public void testExecuteCreateTable() {
        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.execute("CREATE TABLE dept(name varchar(20));");
    }

    public void testInsert() {
        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.insert("INSERT INTO dept(name) values(?)", "IT");
    }

    public void testUpdate() {
        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.insert("update emp set dob=? where id=?", new java.util.Date(), new Integer(2));
    }


}
