package com.epam.copa
package com.epam.copa.infrastructure.driveradapters
import com.epam.copa.com.epam.copa.domain.gateway.PersonaGateway
import  com.epam.copa.com.epam.copa.domain.model.PersonaModel
import com.epam.copa.infrastructure.driveradapters.inmemory.PersonaInMemory
import com.epam.copa.infrastructure.driveradapters.mapper.PersonaMapper

class PersonaAdapter extends  PersonaGateway{

  override def findAll(): List[PersonaModel] = PersonaInMemory.findAll()
    .map(PersonaMapper.toModel)

  override def findById(id: String): Option[PersonaModel] = PersonaInMemory
    .findById(id).map(PersonaMapper.toModel)

  override def save(persona: PersonaModel): PersonaModel = {
    val  entity = PersonaMapper.toDomain(persona)
    val savedEntity = PersonaInMemory.save(entity)
    PersonaMapper.toModel(savedEntity)

  }

  override def existsByDocument(documentNumber: String): Boolean =
    PersonaInMemory.findAll().exists(_.getNumeroDocumento == documentNumber)
}
