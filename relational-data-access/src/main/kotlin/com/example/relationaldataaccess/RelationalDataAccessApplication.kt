package com.example.relationaldataaccess

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class RelationalDataAccessApplication(
    private val jdbcTemplate: JdbcTemplate
) : CommandLineRunner {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(RelationalDataAccessApplication::class.java)
    }

    override fun run(vararg args: String?) {

        log.info("Creating tables")

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS ")
        jdbcTemplate.execute("CREATE TABLE customers(" +
            "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))")

        val splitUpNames = listOf("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
            .map { it.split(" ").toTypedArray() }
            .toList()
        splitUpNames.forEach {
            log.info(String.format("Inserting customer record for %s %s", it[0], it[1]))
        }

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?, ?)", splitUpNames)

        log.info("Querying for customer records where first_name = 'Josh':")
        jdbcTemplate.query(
            "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
            arrayOf("Josh")
        ) { rs, _ ->
            Customer(id = rs.getLong("id"), firstName = rs.getString("first_name"),
                lastName = rs.getString("last_name"))
        }.forEach { log.info(it.toString()) }
    }
}

fun main(args: Array<String>) {
    runApplication<RelationalDataAccessApplication>(*args)
}
