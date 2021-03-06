package com.prototype.auth.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.NotFound
import com.prototype.auth.api.AuthService
import com.prototype.auth.api.model.{TokenRequest, User, Payload}
import com.prototype.auth.api.utilities.JwtUtility
import com.prototype.auth.impl.mapping.Mapping
import com.prototype.auth.impl.utilities.PayloadUtility
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.json.{JsValue, Json}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Failure}

class AuthServiceImpl(userStorage: UserStorage, 
                      mapping: Mapping)(implicit ec: ExecutionContext) extends AuthService {

  private lazy val log: Logger = LoggerFactory.getLogger(classOf[AuthServiceImpl])

  override def token(): ServiceCall[TokenRequest, String] = 
    ServiceCall(request =>
      userStorage.getUser(request.username, request.password)
        .map(_.map(user => 
          JwtUtility.createToken(PayloadUtility.payload(user))).getOrElse(throw NotFound("Invalid username or password."))
        )
    )

}
