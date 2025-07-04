/*
 * Fichier: src/main/scala/com/example/AkkaHttpServer.scala
 * Description: Fichier principal du serveur Akka HTTP.
 */
package com.example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import scala.io.StdIn

// Objet pour définir le format JSON
final case class Message(text: String)
object JsonFormats {
  implicit val messageFormat = jsonFormat1(Message)
}

object AkkaHttpServer {

  def main(args: Array[String]): Unit = {

    // Un système d'acteurs est requis pour Akka HTTP
    implicit val system = ActorSystem(Behaviors.empty, "SimpleAkkaHttpServer")
    // Un contexte d'exécution implicite est nécessaire pour les futures
    implicit val executionContext = system.executionContext

    import JsonFormats._

    // Définition des routes de l'application
    val route =
      concat(
        pathSingleSlash { // Route pour la racine "/"
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Bonjour depuis le serveur Scala/Akka HTTP !</h1>"))
          }
        },
        path("json" / "salutation") { // Route pour "/json/salutation"
          get {
            complete(Message("Ceci est une réponse JSON depuis Akka."))
          }
        }
      )

    // Démarrage du serveur sur le port 8080
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Le serveur est en ligne à http://localhost:8080/\nAppuyez sur RETURN pour arrêter...")
    StdIn.readLine() // Laisse le serveur tourner jusqu'à ce que l'utilisateur appuie sur Entrée
    bindingFuture
      .flatMap(_.unbind()) // Déclenche l'arrêt du serveur
      .onComplete(_ => system.terminate()) // Et ferme le système d'acteurs
  }
}
