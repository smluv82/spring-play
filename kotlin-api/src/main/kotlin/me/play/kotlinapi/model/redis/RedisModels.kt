package me.play.kotlinapi.model.redis


data class RedisStringRequest(
    val key: String,
    val value: String,
    val ttl: Long?
)