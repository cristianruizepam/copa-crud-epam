package com.epam.copa
package com.epam.copa.infrastructure.apatersemail

import sttp.client._
import sttp.client.asynchttpclient.monix._
import sttp.client.circe._
import sttp.model.StatusCode



import com.epam.copa.com.epam.copa.domain.gateway.EmailGateway
import com.epam.copa.com.epam.copa.domain.model.PersonaModel
import com.epam.copa.infrastructure.apatersemail.error._


class EmailGatewayAdapter(baseUrl: String) extends EmailGateway {

  override def sendPersonaCreatedEmail(persona: PersonaModel): Either[InfrastructureError, Unit] = {

    implicit val backend: SttpBackend[Identity, Nothing, NothingT] =
      HttpURLConnectionBackend()

    try {

      val response = basicRequest
        .post(uri"$baseUrl/api/v1/persona")
        .contentType("application/json")
        .body(persona)
        .respond(asString)
        .send()

      response.code match {
        case StatusCode.Ok =>
          Right(())

        case _ =>
          Left(
            InfrastructureHttpError(
              response.code.code,
              response.body.getOrElse("Empty response body")
            )
          )
      }

    } catch {
      case e: Exception =>
        Left(InfrastructureConnectionError(e.getMessage))
    }

  }
}
