package com.epam.copa
package com.epam.copa.infrastructure.entrypoints.api

import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import org.apache.pekko.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}

import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._

import com.epam.copa.com.epam.copa.domain.usecases._
import com.epam.copa.com.epam.copa.domain.error._
import com.epam.copa.infrastructure.entrypoints.api.dto.request.PersonaRequestDTO
import com.epam.copa.infrastructure.entrypoints.api.dto.response.PersonaDTO
import com.epam.copa.infrastructure.entrypoints.api.mapper.PersonaRequestMapper

class PersonasRoutes(
                      findAllUseCase: FindAllPersonasUseCase,
                      findByIdUseCase: FindPersonaByIdUseCase,
                      savePersonaUseCase: SavePersonaUseCase
                    ) {

  private def respondJson(status: StatusCodes.ClientError, json: Json): Route =
    complete(status, HttpEntity(ContentTypes.`application/json`, json.noSpaces))

  private def respondJson(status: StatusCodes.Success, json: Json): Route =
    complete(status, HttpEntity(ContentTypes.`application/json`, json.noSpaces))

  def routes: Route =
    pathPrefix("personas") {

      val findAllRoute: Route =
        pathEndOrSingleSlash {
          get {
            findAllUseCase.execute().fold(
              {
                case PersonasEmpty =>
                  complete(
                    StatusCodes.NotFound,
                    HttpEntity(
                      ContentTypes.`application/json`,
                      Json.obj(
                        "error" -> Json.fromString("PERSONAS_EMPTY"),
                        "message" -> Json.fromString("No existen personas registradas")
                      ).noSpaces
                    )
                  )

                case _ =>
                  complete(
                    StatusCodes.BadRequest,
                    HttpEntity(
                      ContentTypes.`application/json`,
                      Json.obj(
                        "error" -> Json.fromString("DOMAIN_ERROR"),
                        "message" -> Json.fromString("Error de negocio")
                      ).noSpaces
                    )
                  )
              },
              personas =>
                complete(
                  StatusCodes.OK,
                  HttpEntity(
                    ContentTypes.`application/json`,
                    Json.obj(
                      "message" -> Json.fromString("Consulta exitosa"),
                      "data" -> personas.map(PersonaDTO.apply).asJson
                    ).noSpaces
                  )
                )
            )
          }
        }

      val findByIdRoute: Route =
        path(Segment) { id =>
          get {
            findByIdUseCase.execute(id).fold(
              {
                case PersonaNotFound(_) =>
                  complete(
                    StatusCodes.NotFound,
                    HttpEntity(
                      ContentTypes.`application/json`,
                      Json.obj(
                        "error" -> Json.fromString("PERSONA_NOT_FOUND"),
                        "message" -> Json.fromString(s"Persona con id $id no encontrada")
                      ).noSpaces
                    )
                  )

                case _ =>
                  complete(
                    StatusCodes.BadRequest,
                    HttpEntity(
                      ContentTypes.`application/json`,
                      Json.obj(
                        "error" -> Json.fromString("DOMAIN_ERROR"),
                        "message" -> Json.fromString("Error de negocio")
                      ).noSpaces
                    )
                  )
              },
              persona =>
                complete(
                  StatusCodes.OK,
                  HttpEntity(
                    ContentTypes.`application/json`,
                    Json.obj(
                      "message" -> Json.fromString("Consulta exitosa"),
                      "data" -> PersonaDTO.apply(persona).asJson
                    ).noSpaces
                  )
                )
            )
          }
        }

      val savePersonaRoute: Route =
        pathEndOrSingleSlash {
          post {
            entity(as[String]) { body =>
              decode[PersonaRequestDTO](body) match {
                case Right(requestDto) =>
                  val personaModel = PersonaRequestMapper.toModel(requestDto)

                  savePersonaUseCase.execute(personaModel).fold(
                    _ =>
                      complete(
                        StatusCodes.BadRequest,
                        HttpEntity(
                          ContentTypes.`application/json`,
                          Json.obj(
                            "error" -> Json.fromString("DOMAIN_ERROR"),
                            "message" -> Json.fromString("Error de negocio")
                          ).noSpaces
                        )
                      ),
                    personaSaved =>
                      complete(
                        StatusCodes.Created,
                        HttpEntity(
                          ContentTypes.`application/json`,
                          Json.obj(
                            "message" -> Json.fromString("Persona creada correctamente"),
                            "data" -> Json.obj(
                              "idPersona" -> Json.fromString(personaSaved.getIdPersona)
                            )
                          ).noSpaces
                        )
                      )
                  )

                case Left(err) =>
                  complete(
                    StatusCodes.BadRequest,
                    HttpEntity(
                      ContentTypes.`application/json`,
                      Json.obj(
                        "error" -> Json.fromString("INVALID_JSON"),
                        "message" -> Json.fromString(err.getMessage)
                      ).noSpaces
                    )
                  )
              }
            }
          }
        }

      findAllRoute ~ findByIdRoute ~ savePersonaRoute
    }
}

