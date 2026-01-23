package com.epam.copa
package com.epam.copa.com.epam.copa.domain.usecases

import com.epam.copa.com.epam.copa.domain.gateway.PersonaGateway
import com.epam.copa.com.epam.copa.domain.model.PersonaModel
import com.epam.copa.com.epam.copa.domain.error.{DomainError, PersonDuplicated}

class SavePersonaUseCase(gateway: PersonaGateway) {

  def execute(persona: PersonaModel): Either[DomainError, PersonaModel] =
    for {
      _ <- validateNotDuplicated(persona)
    } yield gateway.save(persona)

  private def validateNotDuplicated(persona: PersonaModel): Either[DomainError, Unit] =
    Either.cond(!isDuplicate(persona), (), PersonDuplicated)

  private def isDuplicate(persona: PersonaModel): Boolean =
    gateway.existsByDocument(persona.getNumeroDocumento)

}
