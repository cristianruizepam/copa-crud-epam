package com.epam.copa
package com.epam.copa.infrastructure.entrypoints.api.mapper


import com.epam.copa.infrastructure.entrypoints.api.dto.request.PersonaRequestDTO
import com.epam.copa.com.epam.copa.domain.model.PersonaModel
import com.epam.copa.com.epam.copa.domain.constants.TipoDocumento

object PersonaRequestMapper {

  def toModel(request: PersonaRequestDTO): PersonaModel =
    new PersonaModel(
      TipoDocumento
        .fromCodigo(request.tipoDocumento.trim)
        .getOrElse(TipoDocumento.OTRO),
      numeroDocumento = request.numeroDocumento.trim,
      nombre = request.nombre.trim,
      apellido = request.apellido.trim,
      correo = request.correo.trim,
      telefono = request.telefono.trim
    )
}