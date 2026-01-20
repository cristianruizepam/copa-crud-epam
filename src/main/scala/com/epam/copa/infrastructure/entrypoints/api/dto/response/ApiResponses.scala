package com.epam.copa
package com.epam.copa.infrastructure.entrypoints.api.dto.response

final case class ApiResponse[T] (
  message: String,
  data: T
  )
final case class ErrorResponse (
  error: String,
  message: String
  )