package com.epam.mentoring.avro.producer;

import static com.epam.mentoring.avro.Main.WARM_UP;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;

import com.epam.mentoring.avro.v1.Greeting;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

/**
 * @author Eduard_Sardyka
 */
public class GreetingProducerAppV1 {

    private static final Logger logger = Logger.getLogger(GreetingProducerAppV1.class.getName());

    private final KafkaProducer<String, Greeting> kafkaProducer;

    private final String topic;

    public static final String GREETING_V1_KEY = "greeting-v1";

    public GreetingProducerAppV1(String topic, String kafkaServer, String schemaRegistry) {
        this.topic = topic;
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        props.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, true);
        props.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, false);
        kafkaProducer = new KafkaProducer<>(props);
    }

    public void produce(String greet, String key) {

        Greeting greeting = Greeting
            .newBuilder()
            .setVersion(1)
            .setGreet(greet)
            .setTime(System.currentTimeMillis())
            .build();

        ProducerRecord<String, Greeting> record = new ProducerRecord<>(topic, key, greeting);

        try {
            kafkaProducer.send(record);
            if(!WARM_UP.equals(key)) {
                logger.log(Level.INFO, "Greeting V1 producer produced greeting: " + greeting);
            }
        } catch (SerializationException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    public void shutDown() {
        logger.log(Level.INFO, "Greeting V1 producer is closing.");
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}