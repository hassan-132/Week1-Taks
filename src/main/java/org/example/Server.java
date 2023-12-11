package org.example;
import java.io.IOException;

import com.rabbitmq.client.*;

public class Server {
    private static final String queue = "task1_queue";
    private static final String exchangeName = "task_1_exchange";
    private static final String routingKey = "task_routing_key";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            System.out.println("Receiving message from Client");
            // Declare exchange
            channel.exchangeDeclare(exchangeName, "direct");

            // Declare queue and bind it to the exchange
            channel.queueDeclare(queue, false, false, false, null);
            channel.queueBind(queue, exchangeName, routingKey);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Received message: " + message);
            };

            channel.basicConsume(queue, true, deliverCallback, consumerTag -> { });
            // Close connections
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
