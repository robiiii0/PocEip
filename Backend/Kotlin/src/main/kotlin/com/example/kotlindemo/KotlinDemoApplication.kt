/*
 * Fichier: src/main/kotlin/com/example/kotlindemo/KotlinDemoApplication.kt
 * Description: Fichier principal de l'application Spring Boot en Kotlin.
 */
package com.example.kotlindemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

// @SpringBootApplication active la configuration automatique, le scan des composants, etc.
@SpringBootApplication
class KotlinDemoApplication

// La fonction main utilise la fonction d'assistance `runApplication` de Spring Boot
fun main(args: Array<String>) {
    runApplication<KotlinDemoApplication>(*args)
}

// @RestController indique que cette classe est un contrôleur REST.
// Les réponses des méthodes seront directement écrites dans le corps de la réponse HTTP.
@RestController
class SalutationController {

    // Mappe les requêtes GET sur la racine "/"
    @GetMapping("/")
    fun saluer(): String {
        return "Bonjour depuis le serveur Kotlin/Spring Boot !"
    }

    // Mappe les requêtes GET sur "/json/salutation"
    // Spring Boot convertira automatiquement l'objet Message en JSON.
    @GetMapping("/json/salutation")
    fun saluerJson(): Message {
        return Message("Ceci est un message JSON.")
    }
}

// Une "data class" Kotlin est parfaite pour représenter des objets de données comme des DTOs.
// Elle génère automatiquement les getters, setters, equals(), hashCode(), et toString().
data class Message(val text: String)
