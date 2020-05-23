package com.example.messagingrabbitmq

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class MessagingRabbitmqApplication {
    companion object {
        const val topicExchangeName = "spring-boot-exchange"
        const val queueName = "spring-boot"
    }

    // Spring AMQP では、Queue、TopicExchange、および Binding が
    // トップレベル Spring Bean として適切にセットアップされるように宣言される必要がある
    @Bean
    fun queue(): Queue = Queue(queueName, false)

    @Bean
    fun exchange(): TopicExchange = TopicExchange(topicExchangeName)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding =
        // キューは foo.bar.# のルーティングキーでバインド
        BindingBuilder.bind(queue).to(exchange).with("foo.bar.#")

    @Bean
    fun container(connectionFactory: ConnectionFactory, listenerAdapter: MessageListenerAdapter): SimpleMessageListenerContainer =
        SimpleMessageListenerContainer().apply {
            setConnectionFactory(connectionFactory)
            setQueueNames(queueName)
            setMessageListener(listenerAdapter)
        }

    @Bean
    fun listenerAdapter(receiver: Receiver): MessageListenerAdapter = MessageListenerAdapter(receiver, "receiveMessage")
}

fun main(args: Array<String>) {
    runApplication<MessagingRabbitmqApplication>(*args)
}
