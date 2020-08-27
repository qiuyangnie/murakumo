package com.prototype.auth.impl.utilities

import java.time.Instant
import scala.concurrent.duration.Duration

import play.api.libs.json.{JsValue, Json}

import com.prototype.auth.api.model.{User, Payload}

object PayloadUtility {

  def payload(user: User): String = {
    val jwtPayload: Payload = 
      Payload(
        issuer     = "qiuyang",
        subject    = user.principal,
        audience   = "WebApplication",
        expiration = Instant.now.getEpochSecond + Duration("30 minutes").toSeconds)

    val payloadJson: JsValue = Json.toJson(jwtPayload)
    payloadJson.toString()
  }

}
