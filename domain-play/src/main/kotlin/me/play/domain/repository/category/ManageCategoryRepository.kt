package me.play.domain.repository.category

import me.play.domain.entity.category.ManageCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ManageCategoryRepository : JpaRepository<ManageCategory, Long> {
}