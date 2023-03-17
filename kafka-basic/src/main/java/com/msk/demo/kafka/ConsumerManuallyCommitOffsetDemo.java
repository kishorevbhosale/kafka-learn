package com.msk.demo.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerManuallyCommitOffsetDemo {

    private static final Logger log = LoggerFactory.getLogger(ConsumerManuallyCommitOffsetDemo.class);

    public static void main(String[] args) {
        log.info("Consuming the data from brokers");

        Properties properties = DefaultConfig.getConsumerConfig();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("new_topic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                log.info("topic = {}, partition = {}, offset = {}, key = {}, value = {}",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value());

                // manually commit offsets
                TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                consumer.commitSync(Collections.singletonMap(tp, new OffsetAndMetadata(record.offset() + 1)));
            }
        }

    }
}
