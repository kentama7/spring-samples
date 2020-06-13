package com.example.asyncmethod

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.concurrent.CompletableFuture

@Service
class GitHubLookupService(
    private val restTemplateBuilder: RestTemplateBuilder
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(GitHubLookupService::class.java)
    }

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    @Async
    fun findUser(user: String): CompletableFuture<User> {
        logger.info("Looking up $user")
        val url = "https://api.github.com/users/$user"
        val results = restTemplate.getForObject(url, User::class.java)
        Thread.sleep(1000)
        return CompletableFuture.completedFuture(results)
    }
}