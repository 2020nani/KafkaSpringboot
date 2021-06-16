package br.com.kafka.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/offset "
                    + data.offset() + "/timestamp " + data.timestamp());
        };

        var producer = new KafkaProducer<String, String>(properties());
        for (int i = 0; i < 100; i++) {
            var key = UUID.randomUUID().toString();
            var value = key + " 98787, 9000";
            var record = new ProducerRecord<String, String>("ECCOMERCE_NEW_ORDER", key, value);
            producer.send(record, callback).get();
            var email = "Welcome, Your order will be processing";
            var emailRecord = new ProducerRecord<String, String>("ECCOMERCE_SEND_EMAIL", key, email);
            producer.send(emailRecord, callback).get();
        }
    }
    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
