package org.test02_workmode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Main {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                SendMsg();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                RecvMsg01();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                RecvMsg02();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void SendMsg() throws Exception {
        try (// 获取连接对象
             Connection connection = ConnectionUtil.getConnection();
             // 从连接中创建mq通道
             Channel channel = connection.createChannel()) {
            // 声明（创建）队列
            boolean durable = true; //队列持久化
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

            // 发送消息内容
            for (int i = 0; i < 100; i++) {
                String message = "hello world~~_" + i;
                // 消息持久化MessageProperties.PERSISTENT_TEXT_PLAIN
                channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
                Thread.sleep(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RecvMsg01() throws Exception {
        try (// 获取连接对象
             Connection connection = ConnectionUtil.getConnection();
             // 从连接中创建mq通道
             Channel channel = connection.createChannel();) {
            // 声明（创建）队列
            boolean durable = true; //队列持久化
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
            System.out.println(" [x1] Waiting for messages. To exit press CTRL+C");

            // 设置消息上限
            channel.basicQos(1);

            // 接收到消息时，处理该消息的回调函数
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // 取出消息
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                try {
                    // 处理消息
//                    doWork(message);
                    System.out.println(" [x1] Received '" + message + "'");
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 手动自定义返回确认ack信息
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };

            // 监听队列的消息
            while (true) {
                boolean autoAck = false;
                channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RecvMsg02() throws Exception {
        try (// 获取连接对象
             Connection connection = ConnectionUtil.getConnection();
             // 从连接中创建mq通道
             Channel channel = connection.createChannel();) {
            // 声明（创建）队列
            boolean durable = true; //队列持久化
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
            System.out.println(" [x2] Waiting for messages. To exit press CTRL+C");

            // 设置消息上限
            channel.basicQos(1);

            // 接收到消息时，处理该消息的回调函数
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // 取出消息
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                try {
                    // 处理消息
//                    doWork(message);
                    System.out.println(" [x2] Received '" + message + "'");
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 手动自定义返回确认ack信息
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };

            // 监听队列的消息
            while (true) {
                boolean autoAck = false;
                channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
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
        return factory.newConnection();
    }
}