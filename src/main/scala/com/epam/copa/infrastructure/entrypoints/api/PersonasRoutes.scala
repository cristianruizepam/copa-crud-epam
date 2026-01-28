package com.epam.copa
package com.epam.copa.infrastructure.entrypoints.api

import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import org.apache.pekko.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}

import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._

import monix.execution.Scheduler.Implicits.global

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

  // ---------------------------
  // Helpers JSON (como lo tenías)
  // ---------------------------

  private def respondJson(status: StatusCodes.ClientError, json: Json): Route =
    complete(status, HttpEntity(ContentTypes.`application/json`, json.noSpaces))

  private def respondJson(status: StatusCodes.Success, json: Json): Route =
    complete(status, HttpEntity(ContentTypes.`application/json`, json.noSpaces))

  // ---------------------------
  // Validación DTO
  // ---------------------------

  private def blank(s: String): Boolean =
    s == null || s.trim.isEmpty

  private def missingFields(dto: PersonaRequestDTO): List[String] = {
    val fields = List(
      "tipoDocumento"   -> dto.tipoDocumento,
      "numeroDocumento" -> dto.numeroDocumento,
      "nombre"          -> dto.nombre,
      "apellido"        -> dto.apellido,
      "correo"          -> dto.correo,
      "telefono"        -> dto.telefono
    )
    fields.collect { case (name, value) if blank(value) => name }
  }


  private def validate(dto: PersonaRequestDTO): Either[Route, PersonaRequestDTO] =
    Either.cond(
      dto.isValid,
      dto,
      {
        val missing = missingFields(dto)
        respondJson(
          StatusCodes.BadRequest,
          Json.obj(
            "error" -> Json.fromString("INVALID_REQUEST"),
            "message" -> Json.fromString("Campos requeridos vacíos o faltantes"),
            "data" -> Json.obj(
              "missingFields" -> missing.asJson
            )
          )
        )
      }
    )



  private def domainErrorJson(err: DomainError): (StatusCodes.ClientError, Json) = err match {
    case PersonasEmpty =>
      StatusCodes.NotFound ->
        Json.obj(
          "error" -> Json.fromString("PERSONAS_EMPTY"),
          "message" -> Json.fromString("No existen personas registradas")
        )

    case PersonaNotFound(id) =>
      StatusCodes.NotFound ->
        Json.obj(
          "error" -> Json.fromString("PERSONA_NOT_FOUND"),
          "message" -> Json.fromString(s"Persona con id $id no encontrada")
        )

    case PersonDuplicated =>
      StatusCodes.Conflict ->
        Json.obj(
          "error" -> Json.fromString("PERSON_DUPLICATED"),
          "message" -> Json.fromString("La persona ya existe")
        )

    case InvalidPerson =>
      StatusCodes.BadRequest ->
        Json.obj(
          "error" -> Json.fromString("INVALID_PERSON"),
          "message" -> Json.fromString("Datos de persona inválidos")
        )


    case EmailSendFailed(reason) =>
      StatusCodes.BadRequest ->
        Json.obj(
          "error" -> Json.fromString("EMAIL_SEND_FAILED"),
          "message" -> Json.fromString(reason)
        )

    case _ =>
      StatusCodes.BadRequest ->
        Json.obj(
          "error" -> Json.fromString("DOMAIN_ERROR"),
          "message" -> Json.fromString("Error de negocio")
        )
  }

  private def respondDomainError(err: DomainError): Route = {
    val (status, json) = domainErrorJson(err)
    respondJson(status, json)
  }

  // ---------------------------
  // Routes
  // ---------------------------

  def routes: Route =
    pathPrefix("personas") {

      val findAllRoute: Route =
        pathEndOrSingleSlash {
          get {
            findAllUseCase.execute().fold(
              err => respondDomainError(err),
              personas =>
                respondJson(
                  StatusCodes.OK,
                  Json.obj(
                    "message" -> Json.fromString("Consulta exitosa"),
                    "data" -> personas.map(PersonaDTO.apply).asJson
                  )
                )
            )
          }
        }

      val findByIdRoute: Route =
        path(Segment) { id =>
          get {
            findByIdUseCase.execute(id).fold(
              err => respondDomainError(err),
              persona =>
                respondJson(
                  StatusCodes.OK,
                  Json.obj(
                    "message" -> Json.fromString("Consulta exitosa"),
                    "data" -> PersonaDTO.apply(persona).asJson
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

                case Left(err) =>
                  respondJson(
                    StatusCodes.BadRequest,
                    Json.obj(
                      "error"   -> Json.fromString("INVALID_JSON"),
                      "message" -> Json.fromString(err.getMessage)
                    )
                  )

                case Right(requestDto) =>
                  validate(requestDto).fold(
                    route => route,
                    validDto => {
                      val personaModel = PersonaRequestMapper.toModel(validDto)

                      // execute: EitherT[Task, DomainError, PersonaModel]
                      val resultF = savePersonaUseCase.execute(personaModel).value.runToFuture

                      onSuccess(resultF) {
                        case Left(err) =>
                          respondDomainError(err)

                        case Right(personaSaved) =>
                          respondJson(
                            StatusCodes.Created,
                            Json.obj(
                              "message" -> Json.fromString("Persona creada correctamente"),
                              "data" -> Json.obj(
                                "idPersona" -> Json.fromString(personaSaved.getIdPersona)
                              )
                            )
                          )
                      }
                    }
                  )
              }
            }
          }
        }

      findAllRoute ~ findByIdRoute ~ savePersonaRoute
    }
}