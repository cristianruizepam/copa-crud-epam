package com.epam.copa
package com.epam.copa.infrastructure.driveradapters.mapper

import com.epam.copa.com.epam.copa.domain.model.PersonaModel
import com.epam.copa.com.epam.copa.domain.constants.TipoDocumento
import com.epam.copa.infrastructure.driveradapters.objects.Persona

object PersonaMapper {

  def toDomain(model: PersonaModel): Persona =
    new Persona(
      model.getIdPersona,
      model.getTipoDocumento.codigo,
      model.getNumeroDocumento,
      model.getNombre,
      model.getApellido,
      model.getCorreo,
      model.getTelefono
    )


  def toModel(entity: Persona): PersonaModel = {
    val tipoDocumento: TipoDocumento =
      TipoDocumento
        .fromCodigo(entity.getTipoDocumento)
        .getOrElse(TipoDocumento.OTRO)

    val model = new PersonaModel(
      tipoDocumento,
      entity.getNumeroDocumento,
      entity.getNombre,
      entity.getApellido,
      entity.getCorreo,
      entity.getTelefono
    )

    model.setIdPersona(entity.getId)
    model
  }
}
