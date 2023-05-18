package com.os_project;
import java.io.*;
import java.net.*;
import java.util.*;


class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    
    // driver code
    // user chooes ip and port to be connected to
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }


    public String sendItem(String item) throws IOException {
        out.println(item);
        
        return in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        try{
            Client client = new Client();
            // connect client to server(localhost and port 1234)
            client.startConnection("localhost", 1234);
            // user input
            System.out.println("Enter the item to be sent to server (type 'exit' to quit):");
            Scanner sc = new Scanner(System.in);
            String item = null;
            while (!"exit".equalsIgnoreCase(item)) {
                // reading from user, to be sent to server
                item = sc.nextLine();
                System.out.println("Client sent: " + item + " to server");
                // sending the user input to server
                String response = client.sendItem(item);
                // displaying server reply
                System.out.println("Server replied: " + response);
            }
            // closing the scanner object
            sc.close();
            client.stopConnection();
            }
            catch (IOException e) {
                e.printStackTrace();
                }
            }
    }