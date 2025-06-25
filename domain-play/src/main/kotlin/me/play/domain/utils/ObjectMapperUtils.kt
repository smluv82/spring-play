package me.play.domain.utils

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

private val OBJECT_MAPPER by lazy {
    jacksonMapperBuilder()
        .addModule(JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()
}

fun objectMapper(): ObjectMapper {
    return OBJECT_MAPPER
}


fun redisObjectMapper(): ObjectMapper {
    val objectMapper = jacksonObjectMapper().apply {
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        registerModule(JavaTimeModule())
        registerKotlinModule()
    }

    objectMapper.activateDefaultTyping(
        BasicPolymorphicTypeValidator.builder()
            .allowIfSubType(Object::class.java)
            .build(),
        ObjectMapper.DefaultTyping.NON_FINAL,
        JsonTypeInfo.As.PROPERTY
    )

    return objectMapper
}