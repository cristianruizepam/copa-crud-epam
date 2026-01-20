package com.epam.copa
package com.epam.copa.infrastructure.entrypoints.api

import org.apache.pekko.http.scaladsl.model.StatusCodes
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import com.epam.copa.com.epam.copa.domain.error.PersonasEmpty
import com.epam.copa.com.epam.copa.domain.usecases.FindAllPersonasUseCase
import com.epam.copa.infrastructure.entrypoints.api.dto.response.{ApiResponse, ErrorResponse}
import com.epam.copa.infrastructure.entrypoints.api.dto.response.PersonaDTO

import org.mdedetrich.pekko.http.support.CirceHttpSupport._
import io.circe.generic.auto._



class PersonasRoutes(findAllUseCase: FindAllPersonasUseCase) {

  def routes: Route =
    pathPrefix("personas") {
      pathEndOrSingleSlash {
        get {
          findAllUseCase.execute().fold(
            {

              case PersonasEmpty =>
                complete(
                  StatusCodes.NotFound ->
                    ErrorResponse(
                      error = "PERSONAS_EMPTY",
                      message = "No existen personas registradas"
                    )
                )


              case _ =>
                complete(
                  StatusCodes.BadRequest ->
                    ErrorResponse(
                      error = "DOMAIN_ERROR",
                      message = "Error de negocio"
                    )
                )
            },

            personas =>
              complete(
                StatusCodes.OK ->
                  ApiResponse(
                    message = "Consulta exitosa",
                    data = personas.map(PersonaDTO.apply)
                  )
              )
          )
        }
      }
    }
}