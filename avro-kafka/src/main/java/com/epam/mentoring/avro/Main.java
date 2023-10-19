package com.epam.mentoring.avro;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.mentoring.avro.consumer.GreetingConsumerApp;
import com.epam.mentoring.avro.consumer.UserConsumerApp;
import com.epam.mentoring.avro.producer.GreetingProducerAppV1;
import com.epam.mentoring.avro.producer.GreetingProducerAppV2;
import com.epam.mentoring.avro.producer.UserProducerApp;

/**
 * @author Eduard_Sardyka
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String GREETING_TOPIC = "greeting";
    private static final String USER_TOPIC = "user";
    private static final String KAFKA_SERVER = "localhost:9092";
    private static final String SCHEMA_REGISTRY = "http://localhost:8081";
    public static final String WARM_UP = "warm-up";
    private static final int WARM_UP_TIMES = 7;

    public static void main(String[] args) {
        UserProducerApp userProducerApp = new UserProducerApp(USER_TOPIC, KAFKA_SERVER, SCHEMA_REGISTRY);

        GreetingConsumerApp greetingConsumerApp = new GreetingConsumerApp(GREETING_TOPIC, KAFKA_SERVER,
            SCHEMA_REGISTRY);

        GreetingProducerAppV2 greetingProducerAppV2 = new GreetingProducerAppV2(GREETING_TOPIC, KAFKA_SERVER,
            SCHEMA_REGISTRY);

        GreetingProducerAppV1 greetingProducerAppV1 = new GreetingProducerAppV1(GREETING_TOPIC, KAFKA_SERVER,
            SCHEMA_REGISTRY);

        warmUp(greetingProducerAppV1, greetingProducerAppV2);

        UserConsumerApp userConsumerApp = new UserConsumerApp(USER_TOPIC, KAFKA_SERVER, SCHEMA_REGISTRY,
            greetingProducerAppV1, greetingProducerAppV2);
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(1000L);
                    userProducerApp.produce("user_" + i, "email" + i + "@example.com");
                }
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, e.getMessage());
            } finally {
                userProducerApp.shutDown();
            }
        }).start();
        new Thread(() -> {
            userConsumerApp.consume();
        }).start();
        greetingConsumerApp.consume();
    }

    private static void warmUp(GreetingProducerAppV1 greetingProducerAppV1, GreetingProducerAppV2 greetingProducerAppV2) {
        new Thread(() -> {
            for (int i = 1; i <= WARM_UP_TIMES; i++) {
                greetingProducerAppV2.produce("Hello from greeting producer V2", "UserV2", WARM_UP);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, e.getMessage());
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 1; i <= WARM_UP_TIMES; i++) {
                greetingProducerAppV1.produce("Hello from greeting producer V1", WARM_UP);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, e.getMessage());
                }
            }
        }).start();
    }
}