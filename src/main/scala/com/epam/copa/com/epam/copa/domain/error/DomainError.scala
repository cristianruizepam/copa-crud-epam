package com.epam.copa
package com.epam.copa.com.epam.copa.domain.error

sealed trait DomainError

case object PersonasEmpty extends DomainError
case class PersonaNotFound(id: String) extends DomainError
