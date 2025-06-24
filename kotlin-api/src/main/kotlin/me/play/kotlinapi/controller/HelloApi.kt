package me.play.kotlinapi.controller

import io.swagger.v3.oas.annotations.Hidden
import me.play.domain.common.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1")
@RestController
class HelloApi {

    @Hidden
    @GetMapping
    fun hello(): ApiResponse<Unit> = ApiResponse.success()
}