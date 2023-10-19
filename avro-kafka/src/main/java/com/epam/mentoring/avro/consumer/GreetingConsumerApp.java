package com.epam.mentoring.avro.consumer;

import static com.epam.mentoring.avro.producer.GreetingProducerAppV1.GREETING_V1_KEY;
import static com.epam.mentoring.avro.producer.GreetingProducerAppV2.GREETING_V2_KEY;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Eduard_Sardyka
 */
public class GreetingConsumerApp {

    private final Logger logger = Logger.getLogger(GreetingConsumerApp.class.getName());

    private final Consumer<String, GenericRecord> consumer;

    private final String topic;

    public GreetingConsumerApp(String topic, String kafkaServer, String schemaRegistry) {
        this.topic = topic;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "greeting-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        props.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, true);
        props.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, false);

        this.consumer = new KafkaConsumer<>(props);
    }

    public void consume() {
        consumer.subscribe(List.of(topic));
        try {
            for (int i = 0; i < 15; i++) {
                Thread.sleep(1000L);
                logger.log(Level.INFO, "Greeting consumer tries to consume for the " + i + " time.");
                ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100L));
                for (ConsumerRecord<String, GenericRecord> record : records) {
                    GenericRecord rec = record.value();
                    if(GREETING_V1_KEY.equals(record.key()) && 1 == (int) rec.get("version")) {
                        com.epam.mentoring.avro.v1.Greeting greeting = com.epam.mentoring.avro.v1.Greeting
                            .newBuilder()
                            .setVersion(1)
                            .setGreet((String) rec.get("greet"))
                            .setTime((Long) rec.get("time"))
                            .build();
                        logger.log(Level.INFO, "Greeting consumer consumed message with key: " + record.key()
                            + " ,value contains greeting: " + greeting.toString());
                    }
                    if(GREETING_V2_KEY.equals(record.key()) && 2 == (int) rec.get("version")) {
                        com.epam.mentoring.avro.v2.Greeting greeting = com.epam.mentoring.avro.v2.Greeting
                            .newBuilder()
                            .setVersion(2)
                            .setGreet((String) rec.get("greet"))
                            .setTime((Long) rec.get("time"))
                            .setUsername((String) rec.get("username"))
                            .build();
                        logger.log(Level.INFO, "Greeting consumer consumed message with key: " + record.key()
                            + " ,value contains greeting: " + greeting.toString());
                    }
                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            logger.log(Level.INFO, "Greeting consumer is closing.");
            consumer.close();
        }
    }
}