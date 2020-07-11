package com.prototype.auth.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceAcl, ServiceCall}

import com.prototype.auth.api.model.TokenRequest

trait AuthService extends Service {

  override def descriptor: Descriptor = {
    import Service._
    named("auth")
      .withCalls(
        pathCall("/auth/token", () => token())
      )
      .withAutoAcl(true)
      .withAcls(
        ServiceAcl.forMethodAndPathRegex(Method.OPTIONS, "/.*")
      )
  }

  /**
    * Example: curl http://localhost:9000/auth/token
    */
  def token(): ServiceCall[TokenRequest, String]

}
