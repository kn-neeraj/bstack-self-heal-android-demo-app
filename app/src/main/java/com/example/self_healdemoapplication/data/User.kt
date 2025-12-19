package com.example.self_healdemoapplication.data

data class User(
    val email: String,
    val password: String
)

object DemoUsers {
    val users = listOf(
        User("demo1@example.com", "password123"),
        User("demo2@example.com", "password123"),
        User("testuser@example.com", "password123")
    )

    fun getUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }
}
