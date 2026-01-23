package com.epam.copa
package com.epam.copa.infrastructure.entrypoints.api.dto.request

final case class PersonaRequestDTO(
                                    tipoDocumento: String,
                                    numeroDocumento: String,
                                    nombre: String,
                                    apellido: String,
                                    correo: String,
                                    telefono: String

                                  ) {

  def isValid: Boolean = {
    tipoDocumento.nonEmpty &&
    numeroDocumento.nonEmpty &&
    nombre.nonEmpty &&
    apellido.nonEmpty &&
    correo.nonEmpty &&
    telefono.nonEmpty
  }

}
