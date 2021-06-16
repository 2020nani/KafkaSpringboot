package br.com.kafka.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (var dispatcherOrder = new KafkaDispatcher<Order>()){
            try (var dispatcherEmail = new KafkaDispatcher<String>()) {
                for (int i = 0; i < 5; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);
                    var order = new Order(userId, orderId, amount);
                    dispatcherOrder.send("ECCOMERCE_NEW_ORDER", userId, order);
                    var email = "Welcome, Your order will be processing";
                    dispatcherEmail.send("ECCOMERCE_SEND_EMAIL", userId, email);
                }
            }
        }
    }

}
