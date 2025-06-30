package me.play.domain.entity.product

import jakarta.persistence.*
import me.play.domain.entity.BaseEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "update item_inventory set deleted_at = CURRENT_TIMESTAMP where id = ?")
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "item_inventory")
class ItemInventory(
    @Column(name = "item_id")
    val itemId: Long,
    @Column(name = "stock_quantity")
    val stockQuantity: Int,
    @Column(name = "reserved_quantity")
    val reservedQuantity: Int,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
): BaseEntity<Long>()