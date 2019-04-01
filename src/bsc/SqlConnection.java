/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsc;



import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author Parastoo
 */
public class SqlConnection {
    public static MysqlDataSource dataSource ;
    public static Connection connection;
    public static Statement statement;
    public static void setArgs(String args[]){
        dataSource = new MysqlDataSource();
        dataSource.setUser(args[5]);
        dataSource.setPassword(args[6]);
        dataSource.setServerName(args[3]);
        dataSource.setDatabaseName("codestore");
    }
    public static  void getInstance(){

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static ResultSet execute(String query){
        try {
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet ; 
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
