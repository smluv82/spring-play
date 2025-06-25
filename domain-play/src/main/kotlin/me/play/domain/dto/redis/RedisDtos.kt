package me.play.domain.dto.redis

import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.io.Serial
import java.io.Serializable
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class RedisStringDto(
    val key: String,
    val value: String,
    val ttl: Long
)


data class RedisListDto(
    val key: String,
    val values: List<String>,
    val isLeft: Boolean
)

data class RedisListGetDto(
    val key: String,
    val count: Long? = null,
    val timeout: Long? = null
)


data class RedisExpireDto(
    val key: String,
    val ttl: Long,
    val unit: ChronoUnit
)


data class RedisAnyDto(
    val key: String,
    val value: RedisValueDto,
    val ttl: Long
)

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
data class RedisValueDto(
    val name: String,
    val age: Int,
    val dateTime: LocalDateTime
) : Serializable {
    companion object {
        @Serial
        private const val serialVersionUID = 5612368229524432185L
    }
}