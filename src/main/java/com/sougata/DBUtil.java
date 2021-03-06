package com.sougata;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sougata.exception.DBException;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;

/**
 * Created by sougata on 8/19/14.
 */
public class DBUtil {

    public static Connection getConnection(DataSource ds) {
        try{
                return ds.getConnection();
        }catch (Exception ex){
            throw new DBException(ex);
        }
    }

    public static void close(Connection connection) throws DBException{
        if(connection != null){
            try {
                connection.close();
            }catch (Exception ex){
                throw new DBException(ex);
            }
        }
    }

    public static void close(Statement stmt){
        try {
            if(stmt != null){
                stmt.close();
            }

        }catch (Exception ex){
            throw new DBException(ex);
        }
    }

    public static void close(ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }

        }catch (Exception ex){
            throw new DBException(ex);
        }
    }

    public static void close(ResultSet rs,Statement stmt,Connection conn){
            close(rs);
            close(stmt);
            close(conn);
    }


    public static void close(Statement stmt,Connection connection){
            close(stmt);
            close(connection);
    }

    public static void setParameters(PreparedStatement pstmt,Object... values){

        for(int i=0;i<values.length;i++){
            try {
                Object obj = values[i];
                if (obj instanceof String) {
                    pstmt.setString(i+1, (String) obj);
                }
                if(obj instanceof Integer){
                    pstmt.setInt(i+1,(Integer)obj);
                }
                if(obj instanceof java.util.Date){
                    java.util.Date dt = (java.util.Date)obj;
                    java.sql.Date sqlDt = new java.sql.Date(dt.getTime());
                    pstmt.setDate(i+1,sqlDt);
                }
            }catch (Exception ex){
                throw new DBException(ex);
            }
        }
    }

    public static String buildJSON(ResultSet rs){
        if(rs == null){

            return  Util.responseMessage("No data found in the resultset");

        }
        JsonArray jsonArray = new JsonArray();
        boolean found = false;
        try {


            ResultSetMetaData rsMeta = rs.getMetaData();
            int columnCount = rsMeta.getColumnCount();


            while (rs.next()) {
                found = true;
                JsonObject jsonObject = new JsonObject();
                for(int i=1;i<=columnCount;i++){
                    String columnName = rsMeta.getColumnName(i);
                    int type = rsMeta.getColumnType(i);
                    switch (type){
                        case Types.VARCHAR:
                            jsonObject.addProperty(columnName,rs.getString(columnName));
                            break;
                        case Types.NUMERIC:
                            jsonObject.addProperty(columnName,rs.getDouble(columnName));
                            break;
                        case Types.DOUBLE:
                            jsonObject.addProperty(columnName,rs.getDouble(columnName));
                            break;
                        case Types.INTEGER:
                            jsonObject.addProperty(columnName,rs.getInt(columnName));
                            break;
                        case Types.FLOAT:
                            jsonObject.addProperty(columnName,rs.getFloat(columnName));
                            break;
                        case Types.BIGINT:
                            jsonObject.addProperty(columnName,rs.getDouble(columnName));
                            break;
                        case Types.DECIMAL:
                            jsonObject.addProperty(columnName,rs.getDouble(columnName));
                            break;
                        case Types.BOOLEAN:
                            jsonObject.addProperty(columnName,rs.getBoolean(columnName));
                            break;
                        case Types.CHAR:
                            jsonObject.addProperty(columnName,rs.getString(columnName));
                            break;
                        case Types.DATE:
                            if(rs.getDate(columnName) != null) {
                                jsonObject.addProperty(columnName, rs.getDate(columnName).toString());
                            }else{
                                jsonObject.addProperty(columnName,"");
                            }
                            break;
                        case Types.CLOB:
                            jsonObject.addProperty(columnName,readClobToString(rs.getClob(columnName)));
                            break;
                        default:
                            jsonObject.addProperty(columnName,rs.getString(columnName));
                            break;
                    }


                }
                jsonArray.add(jsonObject);


            }

        }catch (SQLException ex){
            return Util.responseException(ex);
        }
        if(!found){
            return  Util.responseMessage("No data found in the resultset");
        }
        return jsonArray.toString();
    }

    private static String readClobToString(Clob clob){
        if(clob == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            String line = null;
            BufferedReader bufferRead  = new BufferedReader(clob.getCharacterStream());
            while((line=bufferRead.readLine()) != null ) {
                    sb.append(line);
            }
        }catch (SQLException sqe){
            throw new DBException(sqe);
        }catch (IOException ioe){
            throw new DBException(ioe);
        }

        return sb.toString();
    }
}
