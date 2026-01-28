package com.epam.copa
package com.epam.copa.com.epam.copa.domain.usecases

import cats.data.EitherT
import monix.eval.Task

import com.epam.copa.com.epam.copa.domain.gateway.{PersonaGateway, EmailGateway}
import com.epam.copa.com.epam.copa.domain.model.PersonaModel
import com.epam.copa.com.epam.copa.domain.error.{DomainError, PersonDuplicated}

class SavePersonaUseCase(gateway: PersonaGateway, emailGateway: EmailGateway) {

  def execute(persona: PersonaModel): EitherT[Task, DomainError, PersonaModel] =
    for {
      _     <- EitherT.fromEither[Task](validateNotDuplicated(persona))
      saved <- EitherT.liftF(Task.eval(gateway.save(persona))) // save es sync => Task
      _     <- EitherT.fromEither[Task](emailGateway.sendPersonaCreatedEmail(saved)) // si Java cae => Left y corta
    } yield saved

  private def validateNotDuplicated(persona: PersonaModel): Either[DomainError, Unit] =
    Either.cond(!isDuplicate(persona), (), PersonDuplicated)

  private def isDuplicate(persona: PersonaModel): Boolean =
    gateway.existsByDocument(persona.getNumeroDocumento)
}
