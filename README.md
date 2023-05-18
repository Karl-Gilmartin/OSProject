# CS4442-OS- Assignment
## Karl Gilmartin and Pardis Norouzi
### *This project was completed as part of ISE- LM173, Module: CS4442, Stream: OS. Both contributors believe the work completed has been evenly disrupted..*
___



## Installiation (Tested on Windows and Mac OS)
1. Clone the git repository onto your local machine.
2. Go to src->main
3. Run the Server.java file
4. Run one or more of Client.java (ensuring the Server is already running)
5. You can now input key commands such as "recipe."

### Project Description:

The CS4442-OS project focuses on developing an application for the Operating System module. The objective is to design, build, and test a menu server that utilizes threads, sockets, and messaging.

### Overview:

The main goal of this project is to create a menu server application that enables clients to interact with the server and place food orders. The server maintains a comprehensive list of available food items, along with their corresponding counts.

### Functionality:

Server Setup: The menu server is implemented using threads, sockets, and shared memory techniques to facilitate communication with clients.

Food Inventory: The server maintains an up-to-date inventory of available food items. Each food item is associated with a count, indicating the quantity in stock.

Client Interaction: Clients can connect to the server and place orders for the listed foods. They can request specific items from the menu by sending messages to the server.

Order Processing: The server processes incoming orders from clients. It verifies the availability of the requested food item and responds accordingly. If the item is available, the server accepts the order and updates the count. However, if the item is no longer available, the server informs the client about its unavailability.

Testing: The project includes rigorous testing to ensure the functionality, reliability, and scalability of the menu server application. Various scenarios are considered to assess the system's performance and validate its behavior under different conditions.

By implementing this menu server application, the project aims to enhance understanding and practical application of operating system concepts, such as threading, socket communication, and message handling.
