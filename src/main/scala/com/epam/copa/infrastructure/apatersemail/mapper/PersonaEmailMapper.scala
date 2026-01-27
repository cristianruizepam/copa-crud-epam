package com.epam.copa
package com.epam.copa.infrastructure.apatersemail.mapper
import  com.epam.copa.com.epam.copa.domain.model.PersonaModel
import io.circe.Json
object PersonaEmailMapper {

  def toJson(persona: PersonaModel): Json =
    Json.obj(
      "idPersona"       -> Json.fromString(persona.getIdPersona),
      "tipoDocumento"   -> Json.fromString(persona.getTipoDocumento.codigo),
      "numeroDocumento" -> Json.fromString(persona.getNumeroDocumento),
      "nombre"          -> Json.fromString(persona.getNombre),
      "apellido"        -> Json.fromString(persona.getApellido),
      "correo"          -> Json.fromString(persona.getCorreo),
      "telefono"        -> Json.fromString(persona.getTelefono)
    )

}
