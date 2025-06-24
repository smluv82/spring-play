package me.play.kotlinapi.api.redis

import io.swagger.v3.oas.annotations.Operation
import me.play.domain.common.response.ApiResponse
import me.play.kotlinapi.application.redis.RedisApplication
import me.play.kotlinapi.model.redis.RedisStringRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/redis")
@RestController
class RedisApi(
    private val redisApplication: RedisApplication,
) {

    @Operation(summary = "redis string 저장")
    @PostMapping("/string")
    fun saveByString(@RequestBody request: RedisStringRequest): ApiResponse<Unit> {
        redisApplication.saveByString(request)
        return ApiResponse.success()
    }
}