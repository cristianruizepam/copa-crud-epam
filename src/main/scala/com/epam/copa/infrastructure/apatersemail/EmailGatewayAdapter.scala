package com.epam.copa
package com.epam.copa.infrastructure.apatersemail

import com.epam.copa.com.epam.copa.domain.gateway.EmailGateway
import com.epam.copa.com.epam.copa.domain.model.PersonaModel
import com.epam.copa.com.epam.copa.domain.error.{DomainError,EmailSendFailed}
import com.epam.copa.infrastructure.apatersemail.error._

import io.circe.generic.auto._
import sttp.client._
import sttp.client.circe._
import sttp.model.StatusCode

import io.circe.generic.auto._
import sttp.client._
import sttp.client.circe._
import sttp.model.StatusCode

class EmailGatewayAdapter(baseUrl: String) extends EmailGateway {

  override def sendPersonaCreatedEmail(persona: PersonaModel): Either[DomainError, Unit] =
    sendEmailInfra(persona).left.map(toDomainError)

  // --- Infra: aquí sí usas InfrastructureError ---
  private def sendEmailInfra(persona: PersonaModel): Either[InfrastructureError, Unit] = {

    implicit val backend: SttpBackend[Identity, Nothing, NothingT] =
      HttpURLConnectionBackend()

    try {
      val response = basicRequest
        .post(uri"$baseUrl/api/v1/persona")
        .contentType("application/json")
        .body(persona)
        .response(asStringAlways)
        .send()

      response.code match {
        case StatusCode.Ok =>
          Right(())

        case _ =>
          Left(InfrastructureHttpError(response.code.code, response.body))
      }

    } catch {
      case e: Exception =>
        Left(InfrastructureConnectionError(e.getMessage))
    }
  }

  // --- Mapper: InfraError -> DomainError ---
  private def toDomainError(err: InfrastructureError): DomainError =
    EmailSendFailed(err.message)
}