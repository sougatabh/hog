package com.sougata;

import com.sougata.exception.DBException;

import javax.sql.DataSource;
import java.sql.*;


/**
 * Created by sougata on 8/19/14.
 */
public class DefaultDBHandler implements DBHandler {

    private DataSource dataSource;

    public DefaultDBHandler(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void execute(String sql) {
        if (sql == null) {
            throw new DBException("Not valid SQL, please check the SQL again");
        }
        Connection connection = DBUtil.getConnection(dataSource);
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.execute();

        } catch (SQLException ex) {
            throw new DBException(ex);
        } finally {
            DBUtil.close(pstmt, connection);
        }
    }

    @Override
    public void execute(String sql, Object... values) {
        if (sql == null) {
            throw new DBException("Not valid SQL, please check the SQL again");
        }
        if (values == null) {
            throw new DBException("No values has been passed");
        }
        Connection connection = DBUtil.getConnection(dataSource);
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            DBUtil.setParameters(pstmt, values);
            pstmt.execute();
        } catch (SQLException ex) {
            throw new DBException(ex);
        } finally {
            DBUtil.close(pstmt, connection);
        }
    }

    @Override
    public int insert(String sql, Object... values) {
        if (Util.startWithIgnoreCase("insert", sql)) {
            throw new DBException("Not a valid Insert Statement");
        }

        return executeSQL(sql, values);
    }

    private int executeSQL(String sql, Object... values) {
        if (sql == null) {
            throw new DBException("Not valid SQL, please check the SQL again");
        }
        if (values == null) {
            throw new DBException("No Values has been passed");
        }
        Connection connection = DBUtil.getConnection(dataSource);
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            DBUtil.setParameters(pstmt, values);
            return pstmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DBException(ex);
        } finally {
            DBUtil.close(pstmt, connection);
        }
    }

    @Override
    public int update(String sql, Object... values) {
        if (Util.startWithIgnoreCase("update", sql)) {
            throw new DBException("Not a valid update Statement");
        }
        return executeSQL(sql, values);
    }

    @Override
    public int delete(String sql, Object... values) {
        if (Util.startWithIgnoreCase("delete", sql)) {
            throw new DBException("Not a valid delete Statement");
        }
        return executeSQL(sql, values);
    }

    @Override
    public String retrieve(String sql) {
        if (sql == null) {
            throw new DBException("Not valid SQL, please check the SQL again");
        }
        Connection connection = DBUtil.getConnection(dataSource);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String jsonValue = null;
        try {
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            jsonValue = DBUtil.buildJSON(rs);


        } catch (SQLException ex) {
            throw new DBException(ex);
        } finally {
            DBUtil.close(rs, pstmt, connection);
        }
        return jsonValue;
    }

    @Override
    public String retrieve(String sql, Object... values) {
        if (sql == null) {
            throw new DBException("Not valid SQL, please check the SQL again");
        }
        if (values == null) {
            throw new DBException("No values has been passed");
        }
        Connection connection = DBUtil.getConnection(dataSource);
        String jsonValue = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                Object obj = values[i];
                if (obj instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) obj);
                }
                if (obj instanceof String) {
                    pstmt.setString(i + 1, (String) obj);
                }
                if (obj instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) obj);
                }
                if (obj instanceof java.sql.Date) {
                    java.sql.Date sqlDate = new Date(((java.util.Date) obj).getTime());
                    pstmt.setDate(i + 1, sqlDate);
                }
            }
            rs = pstmt.executeQuery();
            jsonValue = DBUtil.buildJSON(rs);

            DBUtil.close(pstmt, connection);
        } catch (SQLException ex) {
            throw new DBException(ex);
        } finally {
            DBUtil.close(rs, pstmt, connection);
        }
        return jsonValue;
    }


}
