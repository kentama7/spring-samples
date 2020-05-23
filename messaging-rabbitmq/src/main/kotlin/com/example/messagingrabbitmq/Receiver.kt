package com.example.messagingrabbitmq

import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class Receiver {

    private val latch = CountDownLatch(1)

    fun receiveMessage(message: String) {
        println("Received <$message>")
        latch.countDown()
    }

    fun getLatch(): CountDownLatch = latch
}