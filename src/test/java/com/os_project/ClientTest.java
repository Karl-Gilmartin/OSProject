package com.os_project;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// public class ClientTest {
//     private TestServer testServer;
//     private Client client;

//     @BeforeEach
//     public void setUp() throws IOException {
//         testServer = new TestServer();
//         new Thread(() -> {
//             try {
//                 testServer.start(1234);
//                 testServer.handleClientConnection();
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         }).start();

//         client = new Client();
//         client.startConnection("localhost", 1234);
//     }

//     @AfterEach
//     public void tearDown() throws IOException {
//         client.stopConnection();
//         testServer.stop();
//     }

//     @Test
//     public void testSendItem() throws IOException {
//         String response = client.sendItem("Hello, Server!");
//         assertEquals("Server received: Hello, Server!", response);
//     }
// }