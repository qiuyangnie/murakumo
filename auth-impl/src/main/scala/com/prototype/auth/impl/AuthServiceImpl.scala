package com.prototype.auth.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.prototype.auth.api.AuthService

import scala.concurrent.{ExecutionContext, Future}

import com.prototype.auth.api.model.TokenRequest

class AuthServiceImpl(implicit ec: ExecutionContext) extends AuthService {

  override def token(): ServiceCall[TokenRequest, NotUsed] = ServiceCall(_ => Future(NotUsed))

}
