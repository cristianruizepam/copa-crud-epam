ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.12"
ThisBuild / organization := "com.epam.copa"

val sttpClientVersion = "2.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "copa-crud",
    idePackagePrefix := Some("com.epam.copa"),
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor-typed" % "1.0.2",
      "org.apache.pekko" %% "pekko-stream"      % "1.0.2",
      "org.apache.pekko" %% "pekko-http"        % "1.0.1",

      "io.circe" %% "circe-core"    % "0.13.0",
      "io.circe" %% "circe-generic" % "0.13.0",
      "io.circe" %% "circe-parser"  % "0.13.0",

      "com.softwaremill.sttp.client" %% "core"  % sttpClientVersion,
      "com.softwaremill.sttp.client" %% "circe" % sttpClientVersion,
      "com.softwaremill.sttp.model" %% "core" % "1.4.7",
      "com.softwaremill.sttp.client" %% "async-http-client-backend-monix" % sttpClientVersion,
      "io.monix" %% "monix" % "3.1.0"
    )
  )