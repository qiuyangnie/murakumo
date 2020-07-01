package com.prototype.auth

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.api.{Descriptor, ServiceLocator}
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer}
import com.prototype.auth.api.AuthService
import com.prototype.auth.impl.AuthServiceImpl
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

class AuthServiceLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication =
    new AuthServiceApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new AuthServiceApplication(context) with LagomDevModeComponents

  override def describeService: Option[Descriptor] = Some(readDescriptor[AuthService])
}

abstract class AuthServiceApplication(context: LagomApplicationContext)
  extends LagomApplication(context) with AhcWSComponents {
  // Bind the service that this server provides
  override def lagomServer: LagomServer = serverFor[AuthService](wire[AuthServiceImpl])
}
