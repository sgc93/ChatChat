package com.chat.Model;

public class Profile {
    private String username;
    private String password;
    Chat chat;
    public Profile(String username, String pass){
        this.username = username;
        this.password = pass;
    }

    public void saveClientData(){
        String sql = "INSERT INTO client (username, password) VALUES('"+this.username+"','" +this.password+"')";
        Database db = new Database(this.username);
        db.insertData(sql);
    }   
}
