package com.msk.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerSendWithCallback {
    private static final Logger log = LoggerFactory.getLogger(ProducerSendWithCallback.class);

    public static void main(String[] args) {
        Properties properties = DefaultConfig.getProducerConfig();

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 10; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>("demo_java", "input record " + i);
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
        }
    }
}


/*
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 0
 partition : 0
 offset : 10
 timeStamp : 1678961073045
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 1
 partition : 0
 offset : 11
 timeStamp : 1678961073062
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 2
 partition : 0
 offset : 12
 timeStamp : 1678961073062
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 3
 partition : 0
 offset : 13
 timeStamp : 1678961073062
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 4
 partition : 0
 offset : 14
 timeStamp : 1678961073062
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 5
 partition : 0
 offset : 15
 timeStamp : 1678961073062
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 6
 partition : 0
 offset : 16
 timeStamp : 1678961073062
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 7
 partition : 0
 offset : 17
 timeStamp : 1678961073062
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 8
 partition : 0
 offset : 18
 timeStamp : 1678961073062
[kafka-producer-network-thread | producer-1] INFO com.msk.demo.kafka.ProducerSendWithCallback -
 topic : demo_java
 data : input record 9
 partition : 0
 offset : 19
 timeStamp : 1678961073063
 */
