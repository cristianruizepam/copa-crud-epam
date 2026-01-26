package com.epam.copa
package com.epam.copa.com.epam.copa.domain.usecases

import com.epam.copa.com.epam.copa.domain.gateway.{PersonaGateway, EmailGateway}
import com.epam.copa.com.epam.copa.domain.model.PersonaModel
import com.epam.copa.com.epam.copa.domain.error.{DomainError, PersonDuplicated}

class SavePersonaUseCase(gateway: PersonaGateway
                         , emailGateway: EmailGateway) {

  def execute(persona: PersonaModel): Either[DomainError, PersonaModel] =
    for {
      _ <- validateNotDuplicated(persona)
    } yield {
      val saved = gateway.save(persona)
      emailGateway.sendPersonaCreatedEmail(saved)
      saved
    }
  private def validateNotDuplicated(persona: PersonaModel): Either[DomainError, Unit] =
    Either.cond(!isDuplicate(persona), (), PersonDuplicated)

  private def isDuplicate(persona: PersonaModel): Boolean =
    gateway.existsByDocument(persona.getNumeroDocumento)

}
