package br.com.kafka.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import br.com.kafka.ecommerce.Order;

import java.util.HashMap;


public class FraudeDetectorService {
    public static void main(String[] args) {
        var fraudeDetectorService = new FraudeDetectorService();
        var service = new KafkaService<Order>(FraudeDetectorService.class.getSimpleName(), "ECCOMERCE_NEW_ORDER",
                      fraudeDetectorService::parse, Order.class, new HashMap<>());
        service.run();
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Processando new order, checking fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order Processada");
    }
}

