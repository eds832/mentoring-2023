package com.epam.mentoring.grpc.client;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.stubs.message.PingRequest;
import com.epam.stubs.message.PongResponse;
import com.epam.stubs.message.PingServiceGrpc;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Eduard_Sardyka
 */
public class PingClient {
    private static Logger logger = Logger.getLogger(PingClient.class.getName());

    private PingServiceGrpc.PingServiceBlockingStub pingServiceBlockingStub;

    private PingClient(Channel channel) {
        pingServiceBlockingStub = PingServiceGrpc.newBlockingStub(channel);
    }

    private PongResponse getResponse(String message) {
        logger.info("PingClient calling the PingService method with message: " + message);
        PingRequest pingRequest = PingRequest.newBuilder().setMessage(message).build();
        return pingServiceBlockingStub.getPong(pingRequest);
    }

    public static void main(String[] args) {
        logger.info("Creating a channel and calling the Ping Client");
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
            .usePlaintext().build();
        PingClient pingClient = new PingClient(channel);
        executeRequestAndLogResponse("Ping", pingClient);
        executeRequestAndLogResponse("Incorrect", pingClient);
        executeRequestAndLogResponse("Ping", pingClient);
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException exception) {
            logger.log(Level.SEVERE, "Channel did not shutdown", exception);
        }
    }

    private static void executeRequestAndLogResponse(String message, PingClient pingClient) {
        PongResponse response = pingClient.getResponse(message);
        logger.info("PongResponse: { message: " + response.getMessage() + ", count: " + response.getCount() + " }");
    }

}
