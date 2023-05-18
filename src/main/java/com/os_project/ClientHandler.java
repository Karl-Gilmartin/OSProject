
package com.os_project;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// ClientHandler class
public class ClientHandler implements Runnable {
	private final Socket clientSocket;

	// Constructor
	public ClientHandler(Socket socket) {
		this.clientSocket = socket;
	}

	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			// Get the output stream of the client
			out = new PrintWriter(clientSocket.getOutputStream(), true);

			// Get the input stream of the client
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				// Writing the received message from the client
				System.out.printf("Sent from the client: %s\n", line);
				out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
					clientSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
