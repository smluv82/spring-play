package me.play.domain.service.order

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class OrderService {

    @Transactional
    fun save() {
        TODO()
    }
}