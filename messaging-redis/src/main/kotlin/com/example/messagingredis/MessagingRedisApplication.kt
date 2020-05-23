package com.example.messagingredis

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import kotlin.system.exitProcess

@SpringBootApplication
class MessagingRedisApplication {
    @Bean
    fun container(connectionFactory: RedisConnectionFactory, listenerAdapter: MessageListenerAdapter): RedisMessageListenerContainer =
        RedisMessageListenerContainer().apply {
            setConnectionFactory(connectionFactory)
            addMessageListener(listenerAdapter, PatternTopic("chat"))
        }

    @Bean
    fun listenerAdapter(receiver: Receiver): MessageListenerAdapter = MessageListenerAdapter(receiver, "receiveMessage")

    @Bean
    fun receiver(): Receiver = Receiver()

    @Bean
    fun template(connectionFactory: RedisConnectionFactory): StringRedisTemplate = StringRedisTemplate(connectionFactory)
}

private val LOGGER: Logger = LoggerFactory.getLogger(MessagingRedisApplication::class.java)

fun main(args: Array<String>) {
    val ctx = runApplication<MessagingRedisApplication>(*args)
    val template = ctx.getBean(StringRedisTemplate::class.java)
    val receiver = ctx.getBean(Receiver::class.java)

    while (receiver.getCount() == 0) {
        LOGGER.info("Sending message...")
        template.convertAndSend("chat", "Hello from Redis!")
        Thread.sleep(500)
    }

    exitProcess(0)
}
