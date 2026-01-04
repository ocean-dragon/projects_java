package org.test06_RPCmode;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Main {
    private final static String QUEUE_NAME = "test_queue_rpc";

    public static void main(String[] args) {
        String input = "";
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. run server");
            System.out.println("2. run client");
            System.out.println("q. quit");
            System.out.print("please choose your choice:");
            input = sc.nextLine();
            System.out.println();
            System.out.println();
            if (!input.equals("q")) {
                if (input.equals("1")) {
                    try {
                        ServerRecv();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (input.equals("2")) {
                    try {
                        ClientSend();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                break;
            }
        }
        sc.close();
    }

    /**
     * 客户端发送RPC请求方法
     * 流程：
     * 1. 创建临时响应队列
     * 2. 生成唯一的correlationId
     * 3. 发送请求消息到服务端队列
     * 4. 监听响应队列，等待服务端返回结果
     * 5. 根据correlationId匹配响应
     */
    public static void ClientSend() throws Exception {
        try (// 获取RabbitMQ连接对象
             Connection connection = ConnectionUtil.getConnection();
             // 从连接中创建通信通道
             Channel channel = connection.createChannel()) {

            // 生成当前请求的唯一标识CorrelationID，用于匹配请求和响应
            String corrId = UUID.randomUUID().toString();

            // 声明一个临时的匿名队列，用于接收服务器的响应
            // 该队列是独占的，连接关闭后自动删除
            String queueName = channel.queueDeclare().getQueue();

            // 设置消息属性
            // correlationId: 请求的唯一标识，服务器会在响应中返回相同的ID
            // replyTo: 指定服务器应该将响应发送到哪个队列（即上面创建的临时队列）
            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)  // 设置关联ID
                    .replyTo(queueName)     // 设置响应队列名称
                    .build();

            // 准备要发送的消息内容
            String message = "can you hear me?";

            // 发送消息到服务端队列
            // 参数说明：
            // 1. 交换机名称（空字符串表示使用默认交换机）
            // 2. 路由键（这里使用队列名称）
            // 3. 消息属性（包含correlationId和replyTo）
            // 4. 消息内容（字节数组）
            channel.basicPublish("", QUEUE_NAME, props, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(message + "sent successfully!");

            // ======对服务器的响应进行确认======

            // 创建CompletableFuture对象，用于异步等待响应结果，该对象可以阻塞等待异步操作完成
            // (即等待异步任务时，令其同步完成的工具对象)
            CompletableFuture<String> response = new CompletableFuture<>();

            // 开始监听响应队列，等待服务器发送响应消息
            // 参数说明：
            // 1. 队列名称：临时响应队列
            // 2. autoAck=true：自动确认消息
            // 3. deliverCallback：接收到消息时的回调函数
            // 4. cancelCallback：消费者被取消时的回调函数
            String ctag = channel.basicConsume(queueName, true, (consumerTag, delivery) -> {
                // 当接收到服务器的响应消息时
                // 检查响应消息的correlationId是否与请求的corrId匹配
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    String RespMsg = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [client] Received '" + RespMsg + "'");
                    // 如果匹配，将响应内容传递给CompletableFuture
                    // 这会唤醒下面阻塞等待的response.get()方法
                    response.complete(RespMsg);
                }
            }, consumerTag -> {
                // 消费者被取消时的回调（这里为空实现）
            });

            // 阻塞等待服务器的响应结果，这行代码会一直等待，直到response.complete()被调用
            // (防止主程序执行下去，因此用同步工具阻塞等待服务器的响应结果)
            String result = response.get();
            // 取消消费者监听（清理资源）
            channel.basicCancel(ctag);
            // 处理响应结果
            // doMission(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务端接收并处理RPC请求方法
     * 流程：
     * 1. 声明服务队列并监听
     * 2. 接收客户端请求
     * 3. 处理请求并生成响应
     * 4. 将响应发送到客户端指定的replyTo队列
     * 5. 手动确认消息已处理
     */
    public static void ServerRecv() throws Exception {
        //使用try-with-resources会自动关闭连接,不使用try-with-resources，让连接保持开启
        // 获取RabbitMQ连接对象
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通信通道
        Channel channel = connection.createChannel();
        // 声明（创建）RPC请求队列
        // 参数说明：
        // 1. 队列名称
        // 2. durable=false：非持久化（重启后队列消失）
        // 3. exclusive=false：非独占（其他连接可以访问）
        // 4. autoDelete=false：不自动删除
        // 5. arguments=null：无额外参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 清空队列中的所有消息（用于测试环境，生产环境慎用）
        channel.queuePurge(QUEUE_NAME);

        // 设置服务端的QoS（服务质量）
        // 每次只处理1条消息，处理完后才接收下一条
        // 这样可以实现负载均衡，防止某个服务端过载
        channel.basicQos(1);

        // 定义消息处理回调函数
        // 当接收到客户端请求时，会调用这个函数
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // 构建响应消息的属性
            // 将客户端发送的correlationId原样返回，客户端用它来匹配响应
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();

            // 从消息体中提取客户端发送的请求内容
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            // 准备响应信息
            String response = "gotcha";

            try {
                // 这里可以进行实际的业务处理
//                    doWork(response);
                System.out.println(" [server] Received '" + message + "'");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 将响应消息发送到客户端指定的响应队列
                // delivery.getProperties().getReplyTo() 获取客户端指定的响应队列名称
                // 参数说明：
                // 1. 交换机名称（空字符串表示使用默认交换机）
                // 2. 路由键（客户端的replyTo队列名称）
                // 3. 消息属性（包含correlationId）
                // 4. 响应内容（字节数组）
                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes(StandardCharsets.UTF_8));

                // 手动确认消息已处理（发送ACK确认）
                // 参数说明：
                // 1. deliveryTag：消息的唯一标识
                // 2. multiple=false：只确认当前消息，不批量确认
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        System.out.println("waiting messages...");

        // 开始监听RPC请求队列
        // 参数说明：
        // 1. 队列名称
        // 2. autoAck=false：手动确认模式（需要显式调用basicAck）
        // 3. deliverCallback：消息处理回调函数
        // 4. cancelCallback：消费者被取消时的回调函数（这里为空实现）
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });

        // 注意：这里没有关闭连接，服务端会一直保持运行状态，持续监听队列
    }
}


/**
 * RabbitMQ连接工具类
 * 用于创建和配置RabbitMQ连接
 */
class ConnectionUtil {
    /**
     * 获取RabbitMQ连接对象
     *
     * @return Connection 连接对象
     * @throws Exception 连接异常
     */
    public static Connection getConnection() throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        // 设置RabbitMQ服务器地址
        factory.setHost("localhost");

        // 设置RabbitMQ服务端口（默认5672）
        factory.setPort(5672);

        // 设置虚拟主机（用于隔离不同应用的消息队列）
        factory.setVirtualHost("testhost");

        // 设置用户名
        factory.setUsername("guest");

        // 设置密码
        factory.setPassword("guest");

        // 【可选】设置连接超时时间
//        factory.setConnectionTimeout(5000); // 5秒
        
        // 通过工厂创建并返回连接对象
        return factory.newConnection();
    }
}