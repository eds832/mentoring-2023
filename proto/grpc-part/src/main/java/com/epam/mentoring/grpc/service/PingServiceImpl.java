package com.epam.mentoring.grpc.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.epam.stubs.message.PingRequest;
import com.epam.stubs.message.PongResponse;

import io.grpc.stub.StreamObserver;

/**
 * @author Eduard_Sardyka
 */
public class PingServiceImpl extends com.epam.stubs.message.PingServiceGrpc.PingServiceImplBase {
    private Logger logger = Logger.getLogger(PingServiceImpl.class.getName());

    private AtomicInteger count = new AtomicInteger(0);

    private final static String PING = "Ping";

    private final static String PONG = "Pong";

    private final static String ERROR = "Incorrect request message";

    @Override
    public void getPong(PingRequest request, StreamObserver<PongResponse> responseObserver) {
        String message;
        int cnt;
        if (PING.equals(request.getMessage())) {
            message = PONG;
            cnt = count.incrementAndGet();
        } else {
            message = ERROR;
            cnt = count.intValue();
        }
        logger.info("PingServiceImpl got message: " + request.getMessage());
        PongResponse pongResponse = PongResponse.newBuilder().setMessage(message).setCount(cnt).build();
        responseObserver.onNext(pongResponse);
        responseObserver.onCompleted();
    }
}
