package com.epam.copa
package com.epam.copa.com.epam.copa.domain.error

sealed trait DomainError

case object PersonasEmpty extends DomainError
case class PersonaNotFound(id: String) extends DomainError
case object PersonDuplicated extends DomainError
case object InvalidPerson extends DomainError
final case class EmailSendFailed(reason: String) extends DomainError
