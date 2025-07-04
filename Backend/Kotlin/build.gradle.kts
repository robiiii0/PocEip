/*
 * Fichier: build.gradle.kts
 * Description: Ce fichier configure le build Gradle pour une application
 * Spring Boot en utilisant Kotlin.
 */
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Plugin Spring Boot pour la gestion des dépendances et la création du jar exécutable
    id("org.springframework.boot") version "3.3.1"
    // Plugin pour la gestion des versions des dépendances Spring
    id("io.spring.dependency-management") version "1.1.5"
    // Plugin Kotlin pour la JVM
    kotlin("jvm") version "1.9.23"
    // Plugin pour rendre les classes et méthodes Kotlin "open" pour Spring AOP
    kotlin("plugin.spring") version "1.9.23"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Dépendance de base pour créer des applications web avec Spring MVC
    implementation("org.springframework.boot:spring-boot-starter-web")
    // Module Jackson pour la sérialisation/désérialisation JSON avec Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // Bibliothèque standard de Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // Dépendances pour les tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Configuration du plugin Kotlin
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

// Configuration pour les tests
tasks.withType<Test> {
    useJUnitPlatform()
}
