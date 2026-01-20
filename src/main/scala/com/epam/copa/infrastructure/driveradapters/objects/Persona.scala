package com.epam.copa
package com.epam.copa.infrastructure.driveradapters.objects

class Persona(
               private var _id: String,
               private var _tipoDocumento: String,
               private var _numeroDocumento: String,
               private var _nombre: String,
               private var _apellido: String,
               private var _correo: String,
               private var _telefono: String
             ) {

  def this() = this("", "", "", "", "", "", "")

  // sin id (para crear antes y luego setId)
  def this(tipoDocumento: String,
           numeroDocumento: String,
           nombre: String,
           apellido: String,
           correo: String,
           telefono: String) =
    this("", tipoDocumento, numeroDocumento, nombre, apellido, correo, telefono)

  def getId: String = _id
  def setId(id: String): Unit = _id = id

  def getTipoDocumento: String = _tipoDocumento
  def setTipoDocumento(tipoDocumento: String): Unit = _tipoDocumento = tipoDocumento

  def getNumeroDocumento: String = _numeroDocumento
  def setNumeroDocumento(numeroDocumento: String): Unit = _numeroDocumento = numeroDocumento

  def getNombre: String = _nombre
  def setNombre(nombre: String): Unit = _nombre = nombre

  def getApellido: String = _apellido
  def setApellido(apellido: String): Unit = _apellido = apellido

  def getCorreo: String = _correo
  def setCorreo(correo: String): Unit = _correo = correo

  def getTelefono: String = _telefono
  def setTelefono(telefono: String): Unit = _telefono = telefono
}