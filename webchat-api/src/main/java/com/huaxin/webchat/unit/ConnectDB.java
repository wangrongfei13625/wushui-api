package com.huaxin.webchat.unit;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: ljj
 * Date: 14-10-28
 * Time: 上午9:26
 * To change this template use File | Settings | File Templates.
 */
public class ConnectDB {
    private String dbType;
    private String dbName;
    private String dbHost;
    private String dbHostPort;
    private String dbUserName;
    private String dbPassword;


    public ConnectDB(String dbType, String dbName, String dbHost, String dbHostPort, String dbUserName, String dbPassword){
        this.dbType = dbType;
        this.dbName = dbName;
        this.dbHost = dbHost;
        this.dbHostPort = dbHostPort;
        this.dbUserName = dbUserName;
        //Config filePathConfig = WebKey.getConfig("546");
        String key = "huaxin";
        this.dbPassword = DESUtil.decode(key,dbPassword);
    }

    public Connection getConnection(){
        Connection conn = null;
        String dbDriver = null;
        String url = null;
        if(dbType.equals("0")){
           dbDriver = "net.sourceforge.jtds.jdbc.Driver";
           url = "jdbc:jtds:sqlserver://"+dbHost+":"+dbHostPort+"/"+dbName;
        }else if(dbType.equals("1")) {
            dbDriver = "oracle.jdbc.OracleDriver";
            url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST="+dbHost+")(PORT="+dbHostPort+"))(LOAD_BALANCE=yes)(CONNECT_DATA=(SERVICE_NAME=orcl)))";
        }
        try{
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(url,dbUserName,dbPassword);
        }catch(ClassNotFoundException e){
            //e.printStackTrace();
        }catch(SQLException e){
            //e.printStackTrace();
        }
        return conn;
    }
}
