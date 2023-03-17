package com.msk.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class);

    public static void main(String[] args) {
        log.info("hello world!");

        //create producer properties
        Properties properties = DefaultConfig.getProducerConfig();
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test-group");

        // create kafka producer
        try (Producer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {
            // Create a record
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("new_topic", "Manually Commit Offset Demo ");

            // Send the record
            kafkaProducer.send(producerRecord);
        }
    }
}
