package  ru.hse.authservice.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.Date

@Entity
data class Session(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val userId: Long,
    val token: String,
    val expires: Date
)
