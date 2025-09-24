package me.play.kotlinapi.controller.order

import io.swagger.v3.oas.annotations.Operation
import me.play.domain.common.response.ApiResponse
import me.play.kotlinapi.application.virtualthread.OrderApplication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/orders")
class OrderApi(
    private val orderApplication: OrderApplication
) {

    @Operation(summary = "virtual-threads 테스트를 위한 가상으로 외부 API 호출 및 사용자 정보 저장 및 주문 가정 API")
    @PostMapping
    fun save(
    ): ApiResponse<Unit> {
        orderApplication.save()
        return ApiResponse.success()
    }
}