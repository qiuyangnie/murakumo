package com.prototype.auth.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.prototype.auth.api.AuthService

import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.Duration

import java.time.Instant

import com.prototype.auth.api.model.{TokenRequest, User, Payload}

class AuthServiceImpl(implicit ec: ExecutionContext) extends AuthService {

  private def payload(user: User): String = {
    val jwtPayload = Payload(
      issuer     = "qiuyang",
      subject    = user.principal,
      audience   = "WebApplication",
      expiration = Instant.now.getEpochSecond + Duration("30 minutes").toSeconds
    )

    val payloadJson: JsValue = Json.toJson(jwtPayload)
    payloadJson.toString()
  }

  override def token(): ServiceCall[TokenRequest, NotUsed] = ServiceCall(_ => Future(NotUsed))

}
