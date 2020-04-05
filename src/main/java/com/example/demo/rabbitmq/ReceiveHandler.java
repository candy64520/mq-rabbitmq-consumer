package com.example.demo.rabbitmq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Package: com.example.demo.rabbitmq
 * @ClassName: ReceiveHandler
 * @Author: 86150
 * @Description: 消费者
 * @Date: 2020/4/5 19:21
 */
@Component
public class ReceiveHandler {

    /**
     * 参数明细
     * @Componet：类上的注解，注册到Spring容器
     * @RabbitListener：方法上的注解，声明这个方法是一个消费者方法，需要指定下面的属性：
     * bindings：指定绑定关系，可以有多个。值是@QueueBinding的数组。@QueueBinding包含下面属性：
     * value：这个消费者关联的队列。值是@Queue，代表一个队列
     * exchange：队列所绑定的交换机，值是@Exchange类型
     * key：队列和交换机绑定的RoutingKey，可指定多个
     */
    // 绑定队列到交换机，同时指定需要订阅的routing key。订阅所有的email
    //监听邮件队列
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_email",durable = "true"),
            exchange = @Exchange(
                    value = "topic.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),key = {"topic.#.email.#","email.*"}))
    public void rece_email(String msg){
        System.out.println("[邮件服务] received:"+msg+" '");
    }
    //监听短信队列
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_sms", durable = "true"),
            exchange = @Exchange(
                    value = "topic.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"topic.#.sms.#"}))
    public void rece_sms(String msg){
        System.out.println(" [短信服务] received : " + msg + "!");
    }

}
