package com.example.messagingredis

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicInteger

class Receiver {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(Receiver::class.java)
    }

    private val counter: AtomicInteger = AtomicInteger()

    fun receiveMessage(message: String) {
        LOGGER.info("Received <$message>")
        counter.incrementAndGet()
    }

    fun getCount(): Int = counter.get()
}