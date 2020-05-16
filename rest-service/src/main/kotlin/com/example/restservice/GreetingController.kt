package com.example.restservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class GreetingController {

    companion object {
        private const val template: String = "Hello, %s!"
        private val counter: AtomicLong = AtomicLong()
    }

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting =
        Greeting(id = counter.incrementAndGet(), content = String.format(template, name))
}