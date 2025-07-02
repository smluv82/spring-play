package me.play.domain.entity.product

import jakarta.persistence.*
import me.play.domain.entity.BaseEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "update item set deleted_at = CURRENT_TIMESTAMP where id = ?")
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "item")
class Item(
    @Column(name = "product_id", nullable = false)
    val productId: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    var product: Product? = null,
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "sku_code", nullable = false)
    val skuCode: String,
    @Column(name = "option_color", nullable = false)
    val optionColor: String,
    @Column(name = "option_size", nullable = false)
    val optionSize: String,
    @Column(name = "description", nullable = false)
    var description: String,
    @Column(name = "price", nullable = false)
    var price: Int,
    @Column(name = "used", nullable = false)
    var used: Boolean = true,
    @Column(name = "main_image_path")
    var mainImagePath: String? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
): BaseEntity<Long>()