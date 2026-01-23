
package com.epam.copa

import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.http.scaladsl.Http

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

// Infraestructura
import com.epam.copa.infrastructure.entrypoints.api.PersonasRoutes
import com.epam.copa.infrastructure.driveradapters.PersonaAdapter
import com.epam.copa.com.epam.copa.domain.usecases._

object Main {

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[Nothing] =
      ActorSystem(Behaviors.empty, "copa-crud-system")

    implicit val ec: ExecutionContextExecutor =
      system.executionContext

    // Gateway (in-memory)
    val personaGateway = new PersonaAdapter

    // Use cases
    val findAllPersonasUseCase =
      new FindAllPersonasUseCase(personaGateway)

    val findPersonaByIdUseCase =
      new FindPersonaByIdUseCase(personaGateway)

    val savePersonaUseCase =
      new SavePersonaUseCase(personaGateway)

    // Routes
    val personasRoutes =
      new PersonasRoutes(
        findAllPersonasUseCase,
        findPersonaByIdUseCase,
        savePersonaUseCase
      )

    // Arranque del servidor HTTP
    val bindingFuture =
      Http()
        .newServerAt("localhost", 8080)
        .bind(personasRoutes.routes)

    bindingFuture.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        println(
          s"Servidor corriendo en http://${address.getHostString}:${address.getPort}/"
        )

      case Failure(ex) =>
        println(s"Error levantando el servidor: ${ex.getMessage}")
        system.terminate()
    }
  }
}