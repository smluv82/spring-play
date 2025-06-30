package me.play.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant


/**
 * JpaAuditingConfig을 통해 자동으로 공통 entity에 대해 값 세팅
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity<ID> {
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
        protected set


    @CreatedBy
    @Column(name = "created_by", nullable = false)
    var createdBy: String = ""
        protected set


    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now()
        protected set


    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    var updatedBy: String = ""
        protected set

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
}