package com.chat.Model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    String client_name;
    public Database(String name){
        this.client_name = name;
        this.createTable("client");
    }

    public static Connection connect(){
        final String URL = "jdbc:sqlite:src\\main\\java\\com\\chat\\Model\\database\\profile.db";
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL);
            System.out.println("connected!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void createTable(String table_name){
        try (Statement st = connect().createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + table_name + " (\n"
                    + "	client_id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	username text NOT NULL,\n"
                    + "	password text NOT NULL\n"
                    + ");";
            System.out.println(sql);
            try {
                st.execute(sql);
                System.out.println("Table is created : " + table_name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void insertData(String sql){
        try(Statement st = connect().createStatement()) {
            st.executeUpdate(sql);
            System.out.println("values Inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getData(String sql){
        try(Statement st = connect().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateDate(String sql){

    }

    public void delData(String sql){

    }
}
