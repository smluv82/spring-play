package me.play.kotlinapi.controller.redis

import io.swagger.v3.oas.annotations.Operation
import me.play.domain.common.response.ApiResponse
import me.play.kotlinapi.application.redis.RedisApplication
import me.play.kotlinapi.model.redis.RedisStringRequest
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/redis")
@RestController
class RedisApi(
    private val redisApplication: RedisApplication,
) {

    @Operation(summary = "redis string 조회")
    @GetMapping("/string/{key}")
    fun getByString(@PathVariable key: String): ApiResponse<String> =
        ApiResponse.success(redisApplication.getByString(key))


    @Operation(summary = "redis string 저장")
    @PostMapping("/string")
    fun saveByString(@RequestBody request: RedisStringRequest): ApiResponse<Unit> {
        redisApplication.saveByString(request)
        return ApiResponse.success()
    }
}