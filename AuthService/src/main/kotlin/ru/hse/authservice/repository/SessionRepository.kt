package ru.hse.authservice.repository

import ru.hse.authservice.model.Session
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface SessionRepository : JpaRepository<Session, Long> {
    fun findByToken(token: String): Session?
    fun findByUserId(userId: Long): Session?
}
