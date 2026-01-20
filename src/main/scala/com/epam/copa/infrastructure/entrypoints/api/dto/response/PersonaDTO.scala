package com.epam.copa
package com.epam.copa.infrastructure.entrypoints.api.dto.response

import com.epam.copa.com.epam.copa.domain.model.PersonaModel

final case class PersonaDTO(idPersona: String,
                            tipoDocumento: String,
                            numeroDocumento: String,
                            nombre: String,
                            apellido: String,
                            correo: String,
                            telefono: String)

object PersonaDTO {
  def apply(persona: PersonaModel): PersonaDTO = PersonaDTO(
    persona.getIdPersona,
    persona.getTipoDocumento.codigo,
    persona.getNumeroDocumento,
    persona.getNombre,
    persona.getApellido,
    persona.getCorreo,
    persona.getTelefono
  )
}