package com.example.springbootdocker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class SpringBootDockerApplication {

    @GetMapping("/")
    fun home(): String = "Hello Docker World"
}

fun main(args: Array<String>) {
    runApplication<SpringBootDockerApplication>(*args)
}
