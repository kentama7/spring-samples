package com.example.actuatorservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ActuatorServiceApplication

fun main(args: Array<String>) {
    runApplication<ActuatorServiceApplication>(*args)
}
