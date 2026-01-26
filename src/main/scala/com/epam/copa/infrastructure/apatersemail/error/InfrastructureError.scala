package com.epam.copa
package com.epam.copa.infrastructure.apatersemail.error

sealed trait InfrastructureError {
  def message: String
}

final case class InfrastructureConnectionError(reason: String) extends InfrastructureError {
  override def message: String = reason
}

final case class InfrastructureHttpError(status: Int, body: String) extends InfrastructureError {
  override def message: String = s"HTTP [$status]: $body"
}

final case class InfrastructureDecodingError(reason: String) extends InfrastructureError {
  override def message: String = reason
}