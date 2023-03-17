package com.msk.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithKey {
    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithKey.class);

    public static void main(String[] args) {

        Properties properties = DefaultConfig.getProducerConfig();

        try (Producer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {

            for (int j = 0; j < 3; j++) {
                for (int i = 1; i < 10; i++) {

                    String topic = "my-new-topic";
                    String key = "my-key";
                    String value = "hello-record-" + i;

                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);

                    kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
                        if (e == null) {
                            log.info("  key : {} | partition : {}", key, recordMetadata.partition());
                        } else {
                            log.info("Error for partition :{} error : ", recordMetadata.partition(), e);
                        }
                    });
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
