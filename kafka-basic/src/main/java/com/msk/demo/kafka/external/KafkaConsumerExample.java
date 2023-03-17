package com.msk.demo.kafka.external;

import com.msk.demo.kafka.DefaultConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerExample.class);

    public static void main(String[] args) {
        String topicName = "kb-topic";
        Properties properties = DefaultConfig.getConsumerConfig();

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singletonList(topicName));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            records.forEach(record -> {
                log.info("Received msg : {}", record.value());
            });
        }

    }
}
