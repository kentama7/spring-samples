package com.example.batchprocessing

//data class Person(
//    val latsName: String,
//    val firstName: String
//)

class Person {
    lateinit var lastName: String
    lateinit var firstName: String

    constructor() {}
    constructor(firstName: String, lastName: String) {
        this.firstName = firstName
        this.lastName = lastName
    }

    override fun toString(): String {
        return "firstName: $firstName, lastName: $lastName"
    }
}