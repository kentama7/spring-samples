package com.example.caching

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class SimpleBookRepository : BookRepository {
    
    @Cacheable("books")
    override fun getByIsbn(isbn: String): Book {
        simulateSlowService()
        return Book(isbn, "Some book")
    }

    private fun simulateSlowService() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            throw IllegalStateException(e)
        }
    }
}