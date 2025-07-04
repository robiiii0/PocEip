/*
 * Fichier: build.sbt (Fichier de build pour sbt)
 * Description: Ce fichier définit les dépendances pour notre projet Scala et Akka HTTP.
 */

val scalaVersionNumber = "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "scala-akka-poc",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scalaVersionNumber,
    libraryDependencies ++= {
      val akkaVersion = "2.9.0"
      val akkaHttpVersion = "10.6.1"
      Seq(
        // Dépendances principales d'Akka HTTP
        "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
        "com.typesafe.akka" %% "akka-stream" % akkaVersion,

        // Dépendance pour le support JSON
        "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

        // Dépendance pour le logging
        "ch.qos.logback" % "logback-classic" % "1.4.14",

        // Dépendances de test
        "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test
      )
    }
  )
