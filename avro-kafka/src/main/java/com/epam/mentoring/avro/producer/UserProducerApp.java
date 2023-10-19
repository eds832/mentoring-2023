package com.epam.mentoring.avro.producer;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;

import com.epam.mentoring.avro.User;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

/**
 * @author Eduard_Sardyka
 */
public class UserProducerApp {

    private final static Logger logger = Logger.getLogger(UserProducerApp.class.getName());

    private final KafkaProducer<String, User> kafkaProducer;

    private final String topic;

    public final static String USER_PRODUCER_KEY = "user-record";

    public UserProducerApp(String topic, String kafkaServer, String schemaRegistry) {
        this.topic = topic;
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        props.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, true);
        props.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, true);
        kafkaProducer = new KafkaProducer<>(props);
    }

    public void produce(String login, String email) {
        User user = User
            .newBuilder()
            .setLogin(login)
            .setEmail(email)
            .build();
        ProducerRecord<String, User> record = new ProducerRecord<>(topic, USER_PRODUCER_KEY, user);

        try {
            kafkaProducer.send(record);
            logger.log(Level.INFO, "User producer produced user: " + user);
        } catch (
            SerializationException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    public void shutDown() {
        logger.log(Level.INFO, "User producer is closing.");
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}