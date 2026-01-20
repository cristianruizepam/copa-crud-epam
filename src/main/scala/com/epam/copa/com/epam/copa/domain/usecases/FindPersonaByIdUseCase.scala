package com.epam.copa
package com.epam.copa.com.epam.copa.domain.usecases

import com.epam.copa.com.epam.copa.domain.gateway.PersonaGateway
import com.epam.copa.com.epam.copa.domain.error.{DomainError, PersonaNotFound}
import com.epam.copa.com.epam.copa.domain.model.PersonaModel

class FindPersonaByIdUseCase (gateway: PersonaGateway){

  def execute(id: String): Either[DomainError, PersonaModel] =
    gateway.findById(id).toRight(PersonaNotFound(id))

}
