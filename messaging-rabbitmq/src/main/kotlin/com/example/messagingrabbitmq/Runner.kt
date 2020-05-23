package com.example.messagingrabbitmq

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class Runner(
    private val rabbitTemplate: RabbitTemplate,
    private val receiver: Receiver
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        println("Sending message...")
        rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!")
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS)
    }
}