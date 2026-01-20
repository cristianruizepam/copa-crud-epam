ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.12"
ThisBuild / organization := "com.epam.copa"

lazy val root = (project in file("."))
  .settings(
    name := "copa-crud",
    idePackagePrefix := Some("com.epam.copa"),

    libraryDependencies ++= Seq(
      // Apache Pekko
      "org.apache.pekko" %% "pekko-actor-typed" % "1.0.2",
      "org.apache.pekko" %% "pekko-stream"      % "1.0.2",
      "org.apache.pekko" %% "pekko-http"        % "1.0.1",

      // Circe
      "io.circe" %% "circe-core"    % "0.14.6",
      "io.circe" %% "circe-generic" % "0.14.6",
      "io.circe" %% "circe-parser"  % "0.14.6",


      "org.mdedetrich" %% "pekko-http-circe" % "1.0.0"
    )
  )
