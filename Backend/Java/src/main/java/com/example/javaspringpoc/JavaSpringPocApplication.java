/*
 * Fichier: src/main/java/com/example/javaspringpoc/JavaSpringPocApplication.java
 * Description: Classe principale de l'application Spring Boot.
 */
package com.example.javaspringpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// @SpringBootApplication est une annotation de commodité qui ajoute:
// @Configuration, @EnableAutoConfiguration, @ComponentScan
@SpringBootApplication
public class JavaSpringPocApplication {

    // La méthode main utilise SpringApplication.run() pour lancer une application.
    public static void main(String[] args) {
        SpringApplication.run(JavaSpringPocApplication.class, args);
    }
}

// @RestController indique que cette classe est un contrôleur où chaque méthode
// renvoie un objet de domaine au lieu d'une vue.
@RestController
class SalutationController {

    // @GetMapping mappe les requêtes HTTP GET sur la méthode de handler spécifique.
    // Ici, elle mappe la racine "/".
    @GetMapping("/")
    public String saluer() {
        return "Bonjour depuis le serveur Java/Spring Boot !";
    }

    // Exemple de route qui renvoie un objet JSON.
    // Spring Boot le convertira automatiquement en JSON.
    @GetMapping("/json/salutation")
    public Map<String, String> saluerJson() {
        return Map.of("message", "Ceci est une réponse JSON.");
    }
}
