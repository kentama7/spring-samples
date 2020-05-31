package com.example.resthateoas

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

class Greeting @JsonCreator constructor(
    @JsonProperty("content")
    private val content: String
) : RepresentationModel<Greeting>() {

    fun getContent(): String = content
}