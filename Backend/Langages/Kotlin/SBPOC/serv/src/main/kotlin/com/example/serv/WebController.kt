package com.example.serv

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class WebController {
    @GetMapping("/")
    fun getRoot(): Map<String, String> {
        return mapOf("message" to "Bonjour, bienvenue sur le serveur Spring Boot !")
    }

    @GetMapping("/items/{itemId}")
    fun getItem(
        @PathVariable itemId: Long,
        @RequestParam(required = false) q: String?
    ): Map<String, Any?> {
        return mapOf(
            "itemId" to itemId,
            "q" to q
        )
    }

    data class User(val userName: String)

    @GetMapping("/utilisateurs")
    fun getUsers(
        @RequestParam(defaultValue = "0") skip: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): List<User> {
        val fakeUsersDb = listOf(User("Alice"), User("Bob"), User("Charlie"))
        val fromIndex = skip.coerceIn(0, fakeUsersDb.size)
        val toIndex = (fromIndex + limit).coerceAtMost(fakeUsersDb.size)

        return fakeUsersDb.subList(fromIndex, toIndex)
    }
}