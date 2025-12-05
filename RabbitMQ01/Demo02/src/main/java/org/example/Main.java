package org.example;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;

public class Main {

    private final static String QUEUE_NAME = "q_test_01";

    public static void main(String[] args) {
        SendMsg();
    }

    public static String SendMsg() {
        String retMsg = "";
        try {
            // 获取到连接以及mq通道
            Connection connection = ConnectionUtil.getConnection();
            // 从连接中创建通道
            Channel channel = connection.createChannel();

            // 声明（创建）队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 消息内容
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            retMsg = "[" + message + "] sent successfully";
            //关闭通道和连接
            channel.close();
            connection.close();
        } catch (Exception e) {
            retMsg = "Exception:" + e.getMessage();
            e.printStackTrace();
        }
        return retMsg;
    }

    public static String RecvMsg() {
        try {
            // 获取到连接以及mq通道
            Connection connection = ConnectionUtil.getConnection();
            // 从连接中创建通道
            Channel channel = connection.createChannel();
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 定义队列的消费者
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    String message = new String(body);
                    System.out.println(" [x] Received '" + message + "'");
                }
            };

            // 监听队列
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (Exception e) {

        }

        return "";
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