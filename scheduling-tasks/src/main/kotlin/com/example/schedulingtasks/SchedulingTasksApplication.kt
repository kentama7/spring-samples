package com.example.schedulingtasks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling // スケジューリングを有効
class SchedulingTasksApplication

fun main(args: Array<String>) {
    runApplication<SchedulingTasksApplication>(*args)
}
