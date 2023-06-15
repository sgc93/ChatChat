package com.chat.Model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chat.utilities.Client;

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
    
    public static Client getClientData(String sql){
        byte[] bytes = null;

        try(Statement st = connect().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            InputStream ppStream;
            while(rs.next()){
                bytes = rs.getBytes("pp");
                if(bytes != null){
                    ppStream = new ByteArrayInputStream(bytes);
                } else {
                    bytes = new byte[0];
                    ppStream = new ByteArrayInputStream(bytes);
                    System.out.println("The user does not have a profile picture.");
                }
                Client client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), ppStream , rs.getInt(7));
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    
    public static void changeProfileInDatabase(byte[] imageData, String username) throws SQLException {
        try (Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement("UPDATE client SET pp = ?, username = ? WHERE current_acc = 1")) {
            stmt.setBytes(1, imageData);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void changeUsername(String username) {
        try (Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement("UPDATE client SET username = ? WHERE current_acc = 1")) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static InputStream getImageStreamByUsername(String username) {
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT pp FROM client WHERE username = ?")) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            byte[] imageData = rs.getBytes("pp");
            if (imageData != null) {
                return new ByteArrayInputStream(imageData);
            }
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;
}

    public static void setNewPassword(String pass) {
        try (Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement("UPDATE client SET password = ? WHERE current_acc = 1")) {
            stmt.setString(1, pass);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
