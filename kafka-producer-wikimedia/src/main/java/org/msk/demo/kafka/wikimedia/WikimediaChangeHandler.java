package org.msk.demo.kafka.wikimedia;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikimediaChangeHandler implements EventHandler {

    private static final Logger log = LoggerFactory.getLogger(WikimediaChangeHandler.class);
    KafkaProducer<String, String> kafkaProducer;
    String topic;

    public WikimediaChangeHandler(KafkaProducer<String, String> kafkaProducer, String topic) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() {
        kafkaProducer.close();
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        log.info(messageEvent.getData());
        // asynchronous
        kafkaProducer.send(new ProducerRecord<>(topic, messageEvent.getData()));
    }

    @Override
    public void onComment(String comment) {
        // nothing here
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error in Stream Reading", t);
    }
}
