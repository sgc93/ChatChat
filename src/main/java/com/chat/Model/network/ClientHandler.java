package com.chat.Model.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> chs = new ArrayList<>();
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;

    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = in.readLine();
            chs.add(this);
            broadcastMessage(username + " has joined the group!");
        } catch (IOException e) {
           closeEverything(socket, in, out);
        }
    }

    @Override
    public void run() {
        String client_message;
        while(socket.isConnected()){
            try {
                client_message = in.readLine();
                System.out.println(client_message);
                broadcastMessage(client_message);
            } catch (IOException e) {
                System.out.println("client is not connected");
                closeEverything(socket, in, out);
                break;
            }
        }
    }
    public void broadcastMessage(String clientMessage) {
        for(ClientHandler ch: chs){
            try {
                if(!ch.username.equals(username)){
                    ch.out.write(clientMessage);
                    ch.out.newLine();
                    ch.out.flush();
                }
            } catch (IOException e) {
               closeEverything(socket, in, out);
            }
        }

    }

    public void removeClient(){
        chs.remove(this);
        broadcastMessage(username + " has left the group!");
    }

    public void closeEverything(Socket socket, BufferedReader in, BufferedWriter out) {
        removeClient();
        try {
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
