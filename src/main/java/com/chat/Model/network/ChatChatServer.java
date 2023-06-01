package com.chat.Model.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatChatServer {
    private ServerSocket serverSocket;
    public ChatChatServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new Client has connected!");

                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
           e.printStackTrace();        // catch 1
        }
    }

    public void closeServerSocket(){
        try {
            if(!serverSocket.isClosed()){
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();        // catch 2
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(41621);
        ChatChatServer server = new ChatChatServer(serverSocket);
        server.startServer();
    }
}
