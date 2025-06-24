package me.play.kotlinapi.model.redis

import io.swagger.v3.oas.annotations.media.Schema
import me.play.domain.dto.redis.RedisStringDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mapstruct.factory.Mappers


@Schema(description = "레디스 string 형 테스트 request model")
data class RedisStringRequest(
    @Schema(description = "redis key")
    val key: String,
    @Schema(description = "redis value")
    val value: String,
    @Schema(description = "redis ttl", defaultValue = "60")
    val ttl: Long?
)


@Mapper
interface RedisModelMapper {

    @Mapping(source = "ttl", target = "ttl", qualifiedByName = ["convertTtl"])
    fun toStringReqDto(request: RedisStringRequest): RedisStringDto

    companion object {
        val INSTANCE: RedisModelMapper = Mappers.getMapper(RedisModelMapper::class.java)

        @Named("convertTtl")
        @JvmStatic
        fun convertTtl(ttl: Long?) = ttl ?: 60
    }
}