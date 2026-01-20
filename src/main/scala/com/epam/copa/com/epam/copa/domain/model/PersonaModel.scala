package com.epam.copa
package com.epam.copa.com.epam.copa.domain.model

import com.epam.copa.com.epam.copa.domain.constants.TipoDocumento

class PersonaModel(
                    private var _idPersona: String,
                    private var _tipoDocumento: TipoDocumento,
                    private var _numeroDocumento: String,
                    private var _nombre: String,
                    private var _apellido: String,
                    private var _correo: String,
                    private var _telefono: String
                  ) {

  def this() =
    this("", TipoDocumento.OTRO, "", "", "", "", "")

  def this(
            tipoDocumento: TipoDocumento,
            numeroDocumento: String,
            nombre: String,
            apellido: String,
            correo: String,
            telefono: String
          ) =
    this("", tipoDocumento, numeroDocumento, nombre, apellido, correo, telefono)

  def getIdPersona: String = _idPersona
  def setIdPersona(idPersona: String): Unit = _idPersona = idPersona

  def getTipoDocumento: TipoDocumento = _tipoDocumento
  def setTipoDocumento(tipoDocumento: TipoDocumento): Unit =
    _tipoDocumento = tipoDocumento

  def getNumeroDocumento: String = _numeroDocumento
  def setNumeroDocumento(numeroDocumento: String): Unit =
    _numeroDocumento = numeroDocumento

  def getNombre: String = _nombre
  def setNombre(nombre: String): Unit = _nombre = nombre

  def getApellido: String = _apellido
  def setApellido(apellido: String): Unit = _apellido = apellido

  def getCorreo: String = _correo
  def setCorreo(correo: String): Unit = _correo = correo

  def getTelefono: String = _telefono
  def setTelefono(telefono: String): Unit = _telefono = telefono

  override def toString: String =
    s"PersonaModel(idPersona=$getIdPersona, tipoDocumento=${_tipoDocumento.codigo}, numeroDocumento=$getNumeroDocumento, nombre=$getNombre, apellido=$getApellido, correo=$getCorreo, telefono=$getTelefono)"
}