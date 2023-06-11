package com.chat.controller;

import java.io.*;
import java.net.*;
import java.util.*;

class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Server started. Waiting for clients...");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // Create a new thread for each client
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

public static void main(String[] args) {
        int port = 12345; // Specify the port number
        Server server = new Server(port);
        server.start();
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private Server server;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    public void run() {
        try {
            // Create input and output streams for the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage;

            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Received message from client: " + clientMessage);
                server.broadcastMessage(clientMessage, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                server.removeClient(this);
                System.out.println("Client disconnected: " + clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}