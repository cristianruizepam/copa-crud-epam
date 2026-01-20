package com.epam.copa
package com.epam.copa.com.epam.copa.domain.usecases
import com.epam.copa.com.epam.copa.domain.error.{DomainError, PersonasEmpty}
import com.epam.copa.com.epam.copa.domain.gateway.PersonaGateway
import com.epam.copa.com.epam.copa.domain.model.PersonaModel

class FindAllPersonasUseCase(gateway: PersonaGateway) {

  def execute(): Either[DomainError, List[PersonaModel]] =
    gateway.findAll() match {
      case Nil => Left(PersonasEmpty)
      case xs  => Right(xs)
    }
}