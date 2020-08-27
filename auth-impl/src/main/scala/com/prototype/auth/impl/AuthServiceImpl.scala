package com.prototype.auth.impl

import akka.NotUsed

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.NotFound

import play.api.libs.json.{JsValue, Json}

import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.Duration
import scala.util.{Success, Failure}

import java.time.Instant

import com.prototype.auth.api.AuthService
import com.prototype.auth.api.model.{TokenRequest, User, Payload}
import com.prototype.auth.api.utilities.JwtUtility
import com.prototype.auth.impl.mapping.UserMapping

class AuthServiceImpl(userStorage: UserStorage, 
                      userMapping: UserMapping)(implicit ec: ExecutionContext) extends AuthService {

  private lazy val log: Logger = LoggerFactory.getLogger(classOf[AuthServiceImpl])

  log.debug("Initialising...")

  userMapping.setup().onComplete {
    case Success(_) => log.debug("USERS table has been successfully created")
    case Failure(exception) => log.error("USERS table could not be created", exception)
  }

  private def payload(user: User): String = {
    val jwtPayload: Payload = 
      Payload(
        issuer     = "qiuyang",
        subject    = user.principal,
        audience   = "WebApplication",
        expiration = Instant.now.getEpochSecond + Duration("30 minutes").toSeconds)

    val payloadJson: JsValue = Json.toJson(jwtPayload)
    payloadJson.toString()
  }

  override def token(): ServiceCall[TokenRequest, String] = ServiceCall(request =>
    userStorage.getUser(request.username, request.password)
      .map(_.map(user => 
        JwtUtility.createToken(payload(user))).getOrElse(throw NotFound("Invalid username or password."))
      )
  )

}
