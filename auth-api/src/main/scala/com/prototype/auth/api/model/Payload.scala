package com.prototype.auth.api.model

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Payload(issuer: String, subject: String, audience: String, expiration: Long)

object Payload {
  final implicit val payloadWrites: Writes[Payload] = (request: Payload) => JsObject(
    Map(
      "iss"   -> JsString(request.issuer),
      "sub"   -> JsString(request.subject),
      "aud"   -> JsString(request.audience),
      "exp"   -> JsNumber(request.expiration),
    )
  )

  implicit val payloadReads: Reads[Payload] = (
    (__ \ "iss").read[String] and 
    (__ \ "sub").read[String] and
    (__ \ "aud").read[String] and
    (__ \ "exp").read[Long]
  ) (Payload.apply _)
}
