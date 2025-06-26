package me.play.kotlinapi.application.redis

import me.play.domain.common.exception.CommonException
import me.play.domain.common.response.ResponseCode
import me.play.domain.service.redis.RedisService
import me.play.kotlinapi.model.redis.RedisModelMapper
import me.play.kotlinapi.model.redis.RedisStringRequest
import org.springframework.stereotype.Service

@Service
class RedisApplication(
    private val redisService: RedisService
) {

    fun getByString(key: String): String {
        return redisService.getByString(key) ?: throw CommonException(ResponseCode.NOT_FOUND_ID)
    }

    fun saveByString(request: RedisStringRequest) {
        val reqDto = RedisModelMapper.INSTANCE.toStringReqDto(request)
        redisService.saveByString(reqDto)
    }
}