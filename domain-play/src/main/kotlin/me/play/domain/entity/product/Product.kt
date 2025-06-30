package me.play.domain.entity.product

import jakarta.persistence.*
import me.play.domain.entity.BaseEntity
import me.play.domain.entity.brand.Brand
import me.play.domain.entity.category.ManageCategory
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "update product set deleted_at = CURRENT_TIMESTAMP where id = ?")
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "product")
class Product(
    @Column(name = "brand_id", nullable = false)
    val brandId: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    var brand: Brand? = null,
    @Column(name = "manage_category_id", nullable = false)
    val manageCategoryId: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    var manageCategory: ManageCategory? = null,
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "description", nullable = false)
    var description: String? = null,
    @Column(name = "model_no", nullable = false)
    val modelNo: String,
    @Column(name = "used", nullable = false)
    var used: Boolean = true,
    @Column(name = "main_image_path")
    var mainImagePath: String? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
): BaseEntity<Long>()