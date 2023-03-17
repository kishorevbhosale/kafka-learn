package com.msk.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerBatchSizeDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerBatchSizeDemo.class);

    public static void main(String[] args) throws InterruptedException {

        Properties properties = DefaultConfig.getProducerConfig();
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,"400");

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int j = 0; j < 10; j++) {
                for (int i = 0; i < 30; i++) {
                    ProducerRecord<String, String> record = new ProducerRecord<>("new_topic", "hello - input-record " + i);
                    producer.send(record, (recordMetadata, e) -> {
                        if (e == null) {
                            log.info(" \n topic : {} \n data : {} \n partition : {} \n offset : {} \n timeStamp : {}",
                                    recordMetadata.topic(), record.value(), recordMetadata.partition(),
                                    recordMetadata.offset(), recordMetadata.timestamp());
                        } else {
                            log.info("Error while sending data to topic : {}, error : {}", recordMetadata.topic(), e);
                        }
                    });
                }
                Thread.sleep(5000);
            }
        }
    }
}

/*
Batching can help improve the efficiency of message production in the following ways:

Reducing network overhead: By sending multiple messages together in a single batch, the producer can reduce the number of
network requests made to the Kafka broker, which can help reduce the network overhead and improve throughput.

Improving compression: By sending multiple messages together in a batch, the producer can apply compression to the entire
batch, which can lead to better compression ratios and reduce the overall size of the data sent over the network.

The batch size can be configured in the producer by setting the batch.size property.
 */