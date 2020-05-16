package com.example.consumingrest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class ConsumingRestApplication {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(ConsumingRestApplication::class.java)
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    @Bean
    fun run(restTemplate: RestTemplate): CommandLineRunner = CommandLineRunner {
        val quote = restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote::class.java)
        log.info(quote.toString())
    }
}

fun main(args: Array<String>) {
    runApplication<ConsumingRestApplication>(*args)
}
