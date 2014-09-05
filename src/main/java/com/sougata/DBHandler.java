package com.sougata;


/**
 * Created by sougata on 8/19/14.
 */

/**
 * The Main DB Handler Interface
 */
public interface DBHandler {
    /**
     * Execute the SQL Queries like create table/insert table etc, But does not take any parameter values.
     *
     * @param sql
     */
    public void execute(String sql);

    /**
     * Execute the SQL Queries like create table/insert table etc, Does take any parameter values.
     *
     * @param sql
     * @param values
     */
    public void execute(String sql, Object... values);

    /**
     * Execute the SQL Queries like insert into table
     *
     * @param sql
     * @param values
     * @return
     */
    public int insert(String sql, Object... values);

    /**
     * Executes update query
     *
     * @param sql
     * @param values
     * @return
     */
    public int update(String sql, Object... values);

    /**
     * @param sql
     * @param values
     * @return
     */
    public int delete(String sql, Object... values);

    /**
     * Returns a JSON String,
     * select name from emp where id  = 1
     *
     * @param sql
     * @return
     */
    public String retrieve(String sql);

    /**
     * Returns JSON String
     * select name from emp value id = ?
     *
     * @param sql
     * @param values
     * @return
     */
    public String retrieve(String sql, Object... values);

}
