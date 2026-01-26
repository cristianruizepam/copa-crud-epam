package com.epam.copa
package com.epam.copa.com.epam.copa.domain.gateway
import  com.epam.copa.com.epam.copa.domain.model.PersonaModel
import com.epam.copa.com.epam.copa.domain.error.DomainError

trait EmailGateway {
  def sendPersonaCreatedEmail(persona: PersonaModel): Either[DomainError, Unit]
}
