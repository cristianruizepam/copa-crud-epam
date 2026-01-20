package com.epam.copa
package com.epam.copa.com.epam.copa.domain.constants

sealed trait TipoDocumento {
  def codigo: String
}

object TipoDocumento {

  case object CC   extends TipoDocumento { val codigo = "CC" }
  case object TI   extends TipoDocumento { val codigo = "TI" }
  case object RC   extends TipoDocumento { val codigo = "RC" }
  case object CE   extends TipoDocumento { val codigo = "CE" }
  case object PA   extends TipoDocumento { val codigo = "PA" }
  case object NIT  extends TipoDocumento { val codigo = "NIT" }
  case object PEP  extends TipoDocumento { val codigo = "PEP" }
  case object PPT  extends TipoDocumento { val codigo = "PPT" }
  case object OTRO extends TipoDocumento { val codigo = "OTRO" }

  val values: Set[TipoDocumento] =
    Set(CC, TI, RC, CE, PA, NIT, PEP, PPT, OTRO)

  def fromCodigo(codigo: String): Option[TipoDocumento] =
    values.find(_.codigo == codigo.trim.toUpperCase)
}