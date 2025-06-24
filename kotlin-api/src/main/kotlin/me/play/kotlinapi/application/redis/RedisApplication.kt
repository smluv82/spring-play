package me.play.kotlinapi.application.redis

import me.play.kotlinapi.model.redis.RedisStringRequest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit

@Service
class RedisApplication(
    private val redisTemplate: RedisTemplate<String, String>,
) {


    fun saveByString(request: RedisStringRequest) {
        redisTemplate.opsForValue().set(request.key, request.value, Duration.of(request.ttl ?: 60, ChronoUnit.MINUTES))
    }
}