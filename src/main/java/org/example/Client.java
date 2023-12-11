package org.example;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Client {
    private static final String msg = "Hello from India";
    private static final String exchangeName = "task_1_exchange";
    private static final String routingKey = "task_routing_key";

    public static void main(String[] args) {
        try {
            // Establish connection
            System.out.println("1st Message from Client");
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            System.out.println("Hello from Client");
            channel.exchangeDeclare(exchangeName, "direct");
            channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
            System.out.println("Message sent: " + msg);
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
