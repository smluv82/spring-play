package me.play.domain.dto.redis

import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serial
import java.io.Serializable
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@Schema(description = "Redis String request model")
data class RedisStringDto(
    val key: String,
    val value: String,
    val ttl: Long,
    val timeUnit: TimeUnit = TimeUnit.MINUTES
)


@Schema(description = "Redis Lists 저장 request model")
data class RedisListDto(
    val key: String,
    val values: List<String>,
    val isLeft: Boolean
)


@Schema(description = "Redis Lists 조회 request model")
data class RedisListGetDto(
    val key: String,
    val count: Long? = null,
    val timeout: Long? = null
)


@Schema(description = "Redis Hash request model")
data class RedisHashDto(
    val key: String,
    val hashKeyToHashValue: Map<String, String>
)


@Schema(description = "Redis ZSet request model")
data class RedisZSetDto(
    val key: String,
    val value: String,
    val score: Double
)


@Schema(description = "Redis ZSet 순위 조회 request model")
data class RedisZSetRankDto(
    val key: String,
    val value: String,
    val reverse: Boolean
)


@Schema(description = "Redis ZSet 범위 조회 request model")
data class RedisZSetRangeDto(
    val key: String,
    val start: Long,
    val end: Long,
    val reverse: Boolean
)


enum class ZSetRemoveType {
    VALUE, RANGE, SCORE
}

@Schema(description = "Redis ZSet 삭제 request model, RANGE인 경우 start, end는 Long, SCORE인 경우 Double형으로 인식")
data class RedisZSetRemoveDto(
    val key: String,
    val removeType: ZSetRemoveType,
    val value: String?,
    val start: Number?,
    val end: Number?
)


@Schema(description = "Redis TTL request model")
data class RedisExpireDto(
    val key: String,
    val ttl: Long,
    val unit: ChronoUnit
)


@Schema(description = "Redis Any 관련 request model")
data class RedisAnyDto(
    val key: String,
    val value: RedisValueDto,
    val ttl: Long
)


@Schema(description = "Redis Any의 Value request model (Json Test)")
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