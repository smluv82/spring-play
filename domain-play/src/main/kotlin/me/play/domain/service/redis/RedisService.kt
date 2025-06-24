package me.play.domain.service.redis

import me.play.domain.dto.redis.RedisStringDto
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun getByString(key: String): String? {
        return redisTemplate.opsForValue()[key]
    }

    fun saveByString(reqDto: RedisStringDto) {
        redisTemplate.opsForValue().set(reqDto.key, reqDto.value, Duration.ofMinutes(reqDto.ttl))
    }

}