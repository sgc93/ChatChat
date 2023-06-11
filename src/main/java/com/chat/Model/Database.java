package com.chat.Model;

import java.sql.Statement;
import java.util.ArrayList;

import com.chat.utilities.Client;

import java.io.IOException;
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
            System.out.println("Acount creation error!");
        }
    }

    public static ArrayList<Client> getClientData(String sql){
        ArrayList<Client> clients = new ArrayList<>();
        try(Statement st = connect().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Client client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), "xxxxxx", rs.getInt(7));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public void updateDate(String sql){

    }

    public void delData(String sql){

    }

    public static String getUserName() throws IOException, SQLException {
        String sql = "SELECT * FROM client";
        String username = null;
        try (Statement st = connect().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            username = rs.getString("username");
        }
        return username;
    }
}
