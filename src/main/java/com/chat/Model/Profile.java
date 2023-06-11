package com.chat.Model;

public class Profile {
    private String username;
    private String password;
    public Profile(String username, String pass){
        this.username = username;
        this.password = pass;
    }

    public void saveClientData(){
        String sql = "INSERT INTO client (username, password) VALUES('"+this.username+"','" +this.password+"')";
        Database db = new Database(username);
        db.insertData(sql);
    }   
}
