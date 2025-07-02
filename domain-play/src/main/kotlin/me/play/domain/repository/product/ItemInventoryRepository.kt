package me.play.domain.repository.product

import me.play.domain.entity.product.ItemInventory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemInventoryRepository : JpaRepository<ItemInventory, Long> {
}