package com.example.batchprocessing

import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class PersonItemProcessor : ItemProcessor<Person, Person> {

    companion object {
        private val log = LoggerFactory.getLogger(PersonItemProcessor::class.java)
    }

    override fun process(person: Person): Person {
        val transformedPerson = Person(person.firstName.toUpperCase(), person.lastName.toUpperCase())
        log.info("Converting ($person) into ($transformedPerson)")
        return transformedPerson
    }
}