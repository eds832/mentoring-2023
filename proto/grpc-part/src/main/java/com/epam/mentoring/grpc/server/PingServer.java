package com.epam.mentoring.grpc.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.mentoring.grpc.service.PingServiceImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * @author Eduard_Sardyka
 */
public class PingServer {

    private static final Logger logger = Logger.getLogger(PingServer.class.getName());


    private static final long RUN_DURATION = 30 * 60 * 1000L;//30 min

    private static final int PORT = 8080;

    private Server server;

    private void startServer() {
        try {
            server = ServerBuilder.forPort(PORT)
                .addService(new PingServiceImpl())
                .build()
                .start();
            logger.info("Ping Server started on port: " + PORT);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    logger.info("Clean server shutdown in case JVM was shutdown");
                    try {
                        PingServer.this.stopServer();
                    } catch (InterruptedException exception) {
                        logger.log(Level.SEVERE, "Server shutdown interrupted", exception);
                    }
                }
            });
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "Server did not start", exception);
        }
    }

    private void stopServer() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PingServer pingServer = new PingServer();
        pingServer.startServer();
        new Thread(() -> {
            try {
                Thread.sleep(RUN_DURATION);
                logger.log(Level.INFO, "PingServer is stopping");
                pingServer.stopServer();
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "Sleep was interrupted", e);
            }
        }).start();
        pingServer.blockUntilShutdown();
    }
}
