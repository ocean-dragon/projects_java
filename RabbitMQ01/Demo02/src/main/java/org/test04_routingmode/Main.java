package org.test04_routingmode;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class Main {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

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
            // 声明（创建）交换机，指定direct模式
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            // 发送消息内容
            for (int i = 0; i < 100; i++) {
                String severity01 = "Info";
                String severity02 = "Warning";
                String severity03 = "Error";
                // 把消息使用KEY做标记发送给交换机
                channel.basicPublish(EXCHANGE_NAME, severity01, null, (severity01 + "_" + i).getBytes(StandardCharsets.UTF_8));
                channel.basicPublish(EXCHANGE_NAME, severity02, null, (severity02 + "_" + i).getBytes(StandardCharsets.UTF_8));
                channel.basicPublish(EXCHANGE_NAME, severity03, null, (severity03 + "_" + i).getBytes(StandardCharsets.UTF_8));
                System.out.println(" [A] Sent '" + i + "'");
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
            // 声明（创建）direct模式的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // 声明（创建）队列
            String queueName = channel.queueDeclare().getQueue();
            // 将队列绑定到交换机上，配置队列只监听KEY的消息
            channel.queueBind(queueName, EXCHANGE_NAME, "Error");

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
                channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {
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
            // 声明（创建）direct模式的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // 声明（创建）队列
            String queueName = channel.queueDeclare().getQueue();
            // 将队列绑定到交换机上，配置队列只监听KEY的消息
            channel.queueBind(queueName, EXCHANGE_NAME, "Info");
            channel.queueBind(queueName, EXCHANGE_NAME, "Warning");

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
                channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
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