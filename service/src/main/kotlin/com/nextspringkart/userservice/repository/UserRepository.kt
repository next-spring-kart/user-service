package com.nextspringkart.userservice.repository

import com.nextspringkart.userservice.entity.Role
import com.nextspringkart.userservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun findByIsActiveTrue(): List<User>
    fun findByRole(role: Role): List<User>
}