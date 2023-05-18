package com.os_project;
import java.io.*;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServerTest {
    private Server server;
    private Thread serverThread;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    @Before
    public void setup() throws IOException {
        // Start the server in a separate thread
        server = new Server();
        serverThread = new Thread(()->server.main(null));
        serverThread.start();

        // Connect to the server
        clientSocket = new Socket("localhost", 1234);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @After
    public void tearDown() throws IOException {
        // Close the client connection and server
        clientSocket.close();
        serverThread.interrupt();
    }
 
    @Test
    public void testFoodItemAvailability() throws IOException {
        // Test requesting an available food item
        out.println("apple");
        String response = in.readLine();
        assertEquals("apple is served. Remaining quantity: 2", response);
    }

    @Test
    public void testFoodItemNotAvailable() throws IOException {
        // Test requesting an unavailable food item
        out.println("Steak");
        String response = in.readLine();
        assertEquals("Requested food item is not available. Please order another food.", response);
    }

    @Test
    public void testFoodItemPrice() throws IOException {
        // Test requesting the price of a food item
        out.println("price");
        String request = in.readLine();
        assertEquals("Please specify the food item for which you want to know the price.", request);

        out.println("carrot");
        String response = in.readLine();
        assertEquals("Price of carrot: $8.99", "Price of carrot: $8.99", response);
    }

}