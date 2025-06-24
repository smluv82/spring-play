package me.play.domain.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder

private val OBJECT_MAPPER by lazy {
    jacksonMapperBuilder()
        .addModule(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
        .configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()
}

fun objectMapper(): ObjectMapper {
    return OBJECT_MAPPER
}