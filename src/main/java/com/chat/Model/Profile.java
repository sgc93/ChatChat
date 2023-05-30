package com.chat.Model;

public class Profile {
    private String username;
    private String ipAddress;
    private String portNum;
    private String password;
    Chat chat;
    public Profile(String username, String ip, String port, String pass){
        this.username = username;
        this.ipAddress = ip;
        this.portNum = port;
        this.password = pass;
    }

    public void saveClientData(){
        String sql = "INSERT INTO client (username, ip, port, password) VALUES('"+this.username+"','"+this.ipAddress+"','"+this.portNum+"','"+this.password+"')";
        Database db = new Database(this.username);
        db.insertData(sql);
    }   
}
