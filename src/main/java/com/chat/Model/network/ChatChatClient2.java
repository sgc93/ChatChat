package com.chat.Model.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatChatClient2 {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;

    public ChatChatClient2(Socket socket, String username){
        try {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, in, out);
        }
    }

    public void sendMessage(){
        try {
            out.write(username);
            out.newLine();
            out.flush();
    
            try (Scanner scanner = new Scanner(System.in)) {
                while(socket.isConnected()){
                    String messageToSend = scanner.nextLine();
                    out.write(username + " : " + messageToSend);
                    out.newLine();
                    out.flush();
                }
            }
        } catch (IOException e) {
            closeEverything(socket, in, out);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable(){
            String messageFromGroupchat;

            @Override
            public void run() {
                try {
                    while(socket.isConnected()){
                        messageFromGroupchat = in.readLine();
                        System.out.println(messageFromGroupchat);
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

    public static void main(String[] args) throws UnknownHostException, IOException{
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter you username for the group chat : ");
            String username = scanner.nextLine();
            Socket socket = new Socket("localhost", 41621);
            ChatChatClient2 client = new ChatChatClient2(socket, username);
            client.listenForMessage();
            client.sendMessage();
        }
    }
    
}