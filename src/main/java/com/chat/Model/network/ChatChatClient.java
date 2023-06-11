package com.chat.Model.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.chat.controller.HomeController;

public class ChatChatClient {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;

    public ChatChatClient(Socket socket, String username){
        try {
            this.socket = socket;
            this.username = username;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything(socket, in, out);
        }
    }

    public void sendMessage(String message){
        try {
            out.write(username + " : " + message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            closeEverything(socket, in, out);
        }
    }

    public void listenForMessage(HomeController homeController){
        new Thread(new Runnable(){
            String messageFromGroupchat;

            @Override
            public void run() {
                try {
                    while(socket.isConnected()){
                        messageFromGroupchat = in.readLine();
                        if (messageFromGroupchat != null) {
         homeController.showMessage(messageFromGroupchat);
                        }
                    }
                } catch (IOException e) {
                    closeEverything(socket, in, out);
                }
            }
        }).start();
    }

    private void closeEverything(Socket socket, BufferedReader in, BufferedWriter out) {
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