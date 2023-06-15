package com.chat.utilities;

import java.io.InputStream;

public class Client {
    private int client_id;
    private String username;
    private String passaword;
    private InputStream ppBytes;
    private int status;
    public Client(int client_id, String username, String password, InputStream ppBytes, int status){
        this.client_id = client_id;
        this.username = username;
        this.passaword = password;
        this.ppBytes = ppBytes;
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

    public void setPpBytes(InputStream bytes){
        this.ppBytes = bytes;
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
    public InputStream getPpStream(){
        return this.ppBytes;
    }
    public int getStatus(){
        return this.status;
    }





}
