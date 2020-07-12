package com.prototype.auth.api.security

import com.lightbend.lagom.scaladsl.api.transport.Forbidden
import com.lightbend.lagom.scaladsl.server.ServerServiceCall

import play.api.libs.json.Json

import com.prototype.auth.api.model.Payload
import com.prototype.auth.api.utilities.JwtUtility

import java.time.Instant

object Secure {

  def authenticate[Request, Response](serverServicecall: ServerServiceCall[Request, Response]): ServerServiceCall[Request, Response] = 
    ServerServiceCall.compose { requestHeader =>
      val authorizationHeader: String = requestHeader.getHeader("Authorization").getOrElse("")
      val token: String = authorizationHeader.split(" +").last
      if (JwtUtility.isValidToken(token)) {
        JwtUtility.decodeToken(token).fold {
          throw Forbidden("Cannot decode token: you do not have permissions to access this resource.")
        } { payload =>
          val payloadObj: Payload = Json.parse(payload).validate[Payload].get
          val tokenHasExpired: Boolean = Instant.now.getEpochSecond > payloadObj.expiration
          if (tokenHasExpired) throw Forbidden("Token has expired: you do not have permissions to access this resource.")
          else serverServicecall
        }
      } else throw Forbidden("Token provided is invalid: you do not have permissions to access this resource.")
    }

}
