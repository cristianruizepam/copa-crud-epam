package com.epam.copa
package com.epam.copa.com.epam.copa.domain.gateway

import  com.epam.copa.com.epam.copa.domain.model.PersonaModel

trait PersonaGateway {

  def findAll(): List[PersonaModel]
  def  findById(id: String): Option[PersonaModel]
  def save(persona: PersonaModel): PersonaModel
  def existsByDocument(documentNumber: String): Boolean
}
