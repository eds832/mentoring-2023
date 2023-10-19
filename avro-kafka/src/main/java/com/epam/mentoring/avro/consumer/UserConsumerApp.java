package com.epam.mentoring.avro.consumer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.epam.mentoring.avro.producer.GreetingProducerAppV1;
import com.epam.mentoring.avro.producer.GreetingProducerAppV2;
import com.epam.mentoring.avro.User;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

import static com.epam.mentoring.avro.producer.GreetingProducerAppV1.GREETING_V1_KEY;
import static com.epam.mentoring.avro.producer.GreetingProducerAppV2.GREETING_V2_KEY;
import static com.epam.mentoring.avro.producer.UserProducerApp.USER_PRODUCER_KEY;

/**
 * @author Eduard_Sardyka
 */
public class UserConsumerApp {

    private final Logger logger = Logger.getLogger(UserConsumerApp.class.getName());

    private final Consumer<String, GenericRecord> consumer;

    private final String topic;

    private final GreetingProducerAppV1 greetingProducerAppV1;

    private final GreetingProducerAppV2 greetingProducerAppV2;

    public UserConsumerApp(String topic, String kafkaServer, String schemaRegistry,
                           GreetingProducerAppV1 greetingProducerAppV1, GreetingProducerAppV2 greetingProducerAppV2) {
        this.topic = topic;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);

        this.consumer = new KafkaConsumer<>(props);
        this.greetingProducerAppV1 = greetingProducerAppV1;
        this.greetingProducerAppV2 = greetingProducerAppV2;
    }

    public void consume() {
        consumer.subscribe(List.of(topic));
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000L);
                logger.log(Level.INFO, "User consumer tries to consume for the " + i + " time.");
                ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100L));
                for (ConsumerRecord<String, GenericRecord> record : records) {
                    GenericRecord rec = record.value();
                    if (USER_PRODUCER_KEY.equals(record.key())) {
                        User user = User
                            .newBuilder()
                            .setLogin((String) rec.get("login"))
                            .setEmail((String) rec.get("email"))
                            .build();
                        logger.log(Level.INFO,
                            "User consumer consumed message with key: " + record.key() + " , value contains user: "
                                + user.toString());
                        if (user.getLogin().matches(".+?(0|2|4|6|8)")) {
                            greetingProducerAppV1.produce("Hello " + user.getLogin() + "!", GREETING_V1_KEY);
                        } else {
                            greetingProducerAppV2.produce("Hi!", user.getLogin(), GREETING_V2_KEY);
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            greetingProducerAppV1.shutDown();
            greetingProducerAppV2.shutDown();
            logger.log(Level.INFO, "User consumer is closing.");
            consumer.close();
        }
    }
}