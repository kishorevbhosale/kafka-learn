package com.msk.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerRetryDemo {
    private static final Logger log = LoggerFactory.getLogger(ProducerRetryDemo.class);

    public static void main(String[] args) {
        log.info("producer retry demo!");
        //create producer properties
        Properties properties = DefaultConfig.getProducerConfig();
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        // Set the number of retries to 3
        properties.setProperty(org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG, "3");
        // Set the retries time for each request as 1000ms
        properties.setProperty(org.apache.kafka.clients.producer.ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "1000");

        Producer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("new_topic", "Retry message " + i);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        log.info("Error sending message {} : {}", record.value(), exception.getMessage());
                        // Handle the failed message and retry sending it
                        producer.send(record, this);
                    } else {
                        log.info("Message sent successfully to {}  partition : {} offset  : {}", metadata.topic(), metadata.partition(), metadata.offset());
                    }
                }
            });
        }
        producer.close();
    }
}