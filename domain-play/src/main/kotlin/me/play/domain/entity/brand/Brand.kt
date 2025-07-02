package me.play.domain.entity.brand

import jakarta.persistence.*
import me.play.domain.entity.BaseEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "update brand set deleted_at = CURRENT_TIMESTAMP where id = ?")
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "brand")
class Brand(
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "image_path")
    var imagePath: String? = null,
    @Column(name = "used", nullable = false)
    var used: Boolean = true,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
): BaseEntity<Long>()