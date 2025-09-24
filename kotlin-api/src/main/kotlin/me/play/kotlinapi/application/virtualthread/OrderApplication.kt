package me.play.kotlinapi.application.virtualthread

import me.play.domain.service.order.OrderService
import org.springframework.stereotype.Service

@Service
class OrderApplication(
    private val orderService: OrderService
) {


    fun save() {
        TODO(
            """
                1. 상품 테이블 조회 (product, item) 
                3. 외부 mock api call (외부 상품이라 주문 가능여부 체크 한다고 가정)
                2. 오더 validation 및 저장
                4. 리워드 제공 여부 체크
                5. 리워드 저장
            """.trimIndent()
        )

    }
}