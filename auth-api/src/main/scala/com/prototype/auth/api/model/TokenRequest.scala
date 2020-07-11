package com.prototype.auth.api.model

import play.api.libs.json.{Format, Json}

case class TokenRequest(username: String, password: String)

object TokenRequest {
  implicit val format: Format[TokenRequest] = Json.format[TokenRequest]
}
