package org.test01_simplemode;

import com.rabbitmq.client.*;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;

public class Main {

    private final static String QUEUE_NAME = "q_test_01";

    public static void main(String[] args) {
        SendMsg();
    }

    public static String SendMsg() {
        String retMsg = "";
        try (// 获取连接对象
             Connection connection = ConnectionUtil.getConnection();
             // 从连接中创建mq通道
             Channel channel = connection.createChannel();) {

            // 声明（创建）队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 发送消息内容
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            retMsg = "[" + message + "] sent successfully";
        } catch (Exception e) {
            retMsg = "Exception:" + e.getMessage();
            e.printStackTrace();
        }
        return retMsg;
    }

    public static String RecvMsg() {
        String retMsg = "";
        try (// 获取连接对象
             Connection connection = ConnectionUtil.getConnection();
             // 从连接中创建mq通道
             Channel channel = connection.createChannel();) {

            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 定义队列的消费者
//            DefaultConsumer consumer = new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
//                    String message = new String(body);
//                    System.out.println("Received：" + message);
//                }
//            };
            // 监听队列
//            channel.basicConsume(QUEUE_NAME, true, consumer);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
            };
            // 监听队列
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });

        } catch (Exception e) {
            retMsg = "Exception:" + e.getMessage();
            e.printStackTrace();
        }

        return retMsg;
    }
}

class ConnectionUtil {

    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("localhost");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("testhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 通过工厂获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}