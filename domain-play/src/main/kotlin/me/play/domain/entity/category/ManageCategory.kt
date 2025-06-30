package me.play.domain.entity.category

import jakarta.persistence.*
import me.play.domain.entity.BaseEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "update manage_category set deleted_at = CURRENT_TIMESTAMP where id = ?")
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "manage_category")
class ManageCategory(
    @Column(name = "root_category_id", nullable = false)
    val rootCategoryId: Long,
    @Column(name = "parent_category_id", nullable = false)
    var parentCategoryId: Long,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "depth", nullable = false)
    val depth: Int,
    @Column(name = "used", nullable = false)
    var used: Boolean = true,
    @Column(name = "is_leaf", nullable = false)
    var isLeaf: Boolean = false,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
): BaseEntity<Long>()