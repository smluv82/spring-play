package me.play.domain.dto.redis

data class RedisStringDto(
    val key: String,
    val value: String,
    val ttl: Long
)