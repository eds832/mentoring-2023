**gRPC part (2 point)**

Create a simple gRPC server application that listens on port 8080 for a request like this (feel free to add more fields)
{ "message": "Ping" }

And responses with something like this (feel free to add more fields)
{ "message": "Pong" }

Create a simple Java gRPC client application that sends a request to the simple server (share the same proto file), gets a response, and prints it.

Create a simple gRPC client application in any other language you like (or ask a trainer/lector for a Python example) that sends a request to the simple server, gets a response, and prints it. Use the same proto file.

Start a server and try to communicate with it using the clients you just wrote.

**Description**

Execute in the root path:  mvn clean install

To start Server: in grpc-part\src\main\java\com\epam\mentoring\grpc\server\PingServer.java run method main.

( mvn exec:java -Dexec.mainClass=com.epam.mentoring.grpc.server.PingServer )

Server runs for 30 min.

To start Client: in grpc-part\src\main\java\com\epam\mentoring\grpc\client\PingClient.java run method main.

( mvn exec:java -Dexec.mainClass=com.epam.mentoring.grpc.client.PingClient )

Client runs for less than 10 sec.

Client will print messages from request and response.

To stop Server: stop main method in grpc-part\src\main\java\com\epam\mentoring\grpc\server\PingServer.java

Nodejs client is in grpc-nodejs folder.
