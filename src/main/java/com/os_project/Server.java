package com.os_project;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server {
	private static Map<String, FoodItem> foodItems;
	private static Map<String, Integer> sharedMemory; // Shared memory

	public static void main(String[] args) {

		sharedMemory = new ConcurrentHashMap<>(); // Initialize shared memory

		// Create a map to store the available food items and their quantities
		foodItems = new ConcurrentHashMap<>();
		foodItems.put("apple", new FoodItem("apple", 3, 5.99));
		foodItems.put("carrot", new FoodItem("carrot", 2, 8.99));
		foodItems.put("potatoes", new FoodItem("potatoes", 4, 1.99));
		foodItems.put("pistachio", new FoodItem("pistachio", 1, 6.99));

		ServerSocket serverSocket = null;

		try {
			// Listen on port 1234, chosen port, must match the port in Client.java
			serverSocket = new ServerSocket(1234); 
			System.out.println("Server started. Waiting for connections...");

			while (true) {
				// Accept client connections
				Socket clientSocket = serverSocket.accept(); 
				System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

				// Create a new thread to handle the client connection
				// Create a new ClientHandler thread for each client connection
				ClientHandler clientHandler = new ClientHandler(clientSocket);
				Thread thread = new Thread(clientHandler);
				System.out.println("Thread assigned");
				thread.start();
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
					System.out.println("Server socket closed.");
				} catch (IOException e) {
					System.err.println("Error closing server socket: " + e.getMessage());
				}
			}
		}

	}

	// Thread class to handle client connections concurrently
	private static class ClientHandler implements Runnable {
		private Socket clientSocket;
		private BufferedReader in;
		private PrintWriter out;

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		@Override
		public void run() {
			try {
				// Create input and output streams for the client socket
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream(), true);

				while (true) {
					String requestedFoodItem = in.readLine();
					System.out.println(
							"Received " + (requestedFoodItem.equalsIgnoreCase("quit") ? "request" : "food item request")
									+ ": " + requestedFoodItem);

					if (requestedFoodItem == null || requestedFoodItem.equalsIgnoreCase("quit")) {
						out.println("Server terminated.");
						break;
					} else if (requestedFoodItem.equalsIgnoreCase("price")) {
						out.println("Please specify the food item for which you want to know the price.");
						String foodItemToPrice = in.readLine();
						if (foodItemToPrice != null) {
							foodItemToPrice = foodItemToPrice.toLowerCase(); // Convert to lowercase
							if (foodItems.containsKey(foodItemToPrice)) {
								FoodItem foodItem = foodItems.get(foodItemToPrice);
								out.println("Price of " + foodItemToPrice + ": $" + foodItem.getPrice());
							} else {
								out.println("Food item not found.");
							}
						}
					}
					// If the user wants to know the recipe of a food item using GPT
					else if (requestedFoodItem.equalsIgnoreCase("recipe")) {
                        out.println("Please specify the food item for which you want to know the recipe.");
                        String foodItemToRecipe = in.readLine();
						// Convert to lowercase
                        if (foodItemToRecipe != null) {
                            foodItemToRecipe = foodItemToRecipe.toLowerCase(); 
                            // Check if the food item is in the map
							if (foodItems.containsKey(foodItemToRecipe)) {
                                GPT gpt = new GPT();
                                String gpt_input = "Give me a rescipe that uses" + foodItemToRecipe;
                            try{
                                //removing new line characters and replacing them with spaces
                                String input = gpt.chatGPT(gpt_input).replace("\n", "");
                                out.println(input);
                                
                            }
                            catch(Exception e){
                                System.out.println("GPT failed");
                            }
                            } else {
                                out.println("Food item not found.");
                            }
						}
					}

					
					else {
						requestedFoodItem = requestedFoodItem.toLowerCase(); // Convert to lowercase
						FoodItem foodItem = foodItems.get(requestedFoodItem);
						if (foodItem != null && foodItem.getQuantity() > 0) {
							int remainingQuantity = decreaseQuantity(requestedFoodItem); // Decrease quantity using
																							// shared memory
							String response = foodItem.getName() + " is served. Remaining quantity: "
									+ remainingQuantity;

							out.println(response);
						} else {
							out.println("Requested food item is not available. Please order another food.");
						}
					}
				}

				clientSocket.close();
				System.out.println("Client connection closed.");
			} catch (IOException e) {
				System.err.println("Error handling client request: " + e.getMessage());
			} finally {
				if (in != null) {
					try {
						in.close();
						System.out.println("Input stream closed.");
					} catch (IOException e) {
						System.err.println("Error closing input stream: " + e.getMessage());
					}
				}
				if (out != null) {
					out.close();
					System.out.println("Output stream closed.");
				}
				if (clientSocket != null) {
					try {
						clientSocket.close();
						System.out.println("Client socket closed.");
					} catch (IOException e) {
						System.err.println("Error closing client socket: " + e.getMessage());
					}
				}
			}
		}

		// Decrease quantity of a food item using shared memory
		private int decreaseQuantity(String foodItemName) {
			synchronized (sharedMemory) {
				if (sharedMemory.containsKey(foodItemName)) {
					int quantity = sharedMemory.get(foodItemName);
					if (quantity > 0) {
						quantity--;
						sharedMemory.put(foodItemName, quantity);
						return quantity;
					}
				}
			}
			return 0;
		}
	}

	private static class FoodItem {
		private String name;
		private int quantity;
		private double price;

		public FoodItem(String name, int quantity, double price) {
			this.name = name;
			this.quantity = quantity;
			this.price = price;
			sharedMemory.put(name, quantity); 
			// Add food item to shared memory
		}

		public String getName() {
			return name;
		}

		public int getQuantity() {
			return quantity;
		}

		public double getPrice() {
			return price;
		}
	}
}
