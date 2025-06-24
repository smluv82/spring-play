package me.play.kotlinapi.api

import me.play.domain.common.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1")
@RestController
class HelloApi {

    @GetMapping
    fun hello(): ApiResponse<Unit> = ApiResponse.success()
}