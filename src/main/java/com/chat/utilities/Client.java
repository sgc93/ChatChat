package com.chat.utilities;

public class Client {
    private int client_id;
    private String username;
    private String passaword;
    private String pp_path;
    private int status;
    public Client(int client_id, String username, String password, String pp_path, int status){
        this.client_id = client_id;
        this.username = username;
        this.passaword = password;
        this.pp_path = pp_path;
        this.status = status;
    }

    public void setClientId(int id){
        this.client_id = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.passaword = password;
    }

    public void setPpPath(String pp_path){
        this.pp_path = pp_path;
    }

    public void setStatus(int status){
        this.status = status;
    }
    
    public int getClientId(){
        return this.client_id;
    }
    public String getUserName(){
        return this.username;
    }
    public String getPassword(){
        return this.passaword;
    }
    public String getPpPath(){
        return this.pp_path;
    }
    public int getStatus(){
        return this.status;
    }





}
