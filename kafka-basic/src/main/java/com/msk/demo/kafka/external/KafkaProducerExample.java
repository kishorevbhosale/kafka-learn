package com.msk.demo.kafka.external;

import com.msk.demo.kafka.DefaultConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class KafkaProducerExample {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerExample.class);
    public static void main(String[] args) {
        String topicName = "kb-topic";
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";

        Properties properties = DefaultConfig.getProducerConfig();

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ProducerRecord<String, String> record = new ProducerRecord<>(topicName, jsonObject.toString());
                producer.send(record);
                log.info("record details : {}", record.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
