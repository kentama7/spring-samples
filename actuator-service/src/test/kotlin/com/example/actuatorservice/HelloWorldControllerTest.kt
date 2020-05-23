package com.example.actuatorservice

import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ["local.management.port=0"])
class HelloWorldControllerTest(
    @LocalServerPort
    private val port: Int,
    @Value("\${local.management.port}")
    private val mgt: Int,
    private val testRestTemplate: TestRestTemplate
) {
    @Test
    fun shouldReturn200WhenSendingRequestToController() {
        val entity = testRestTemplate.getForEntity("http://localhost:$port/hello-world", Map::class.java)

        then(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun shouldReturn200WhenSendingRequestToManagementEndpoint() {
        val entity = testRestTemplate.getForEntity("http://localhost:$mgt/actuator/info", Map::class.java)

        then(entity.statusCode).isEqualTo(HttpStatus.OK)
    }
}