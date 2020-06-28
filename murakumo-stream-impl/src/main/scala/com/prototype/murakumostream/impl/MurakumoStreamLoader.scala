package com.prototype.murakumostream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.prototype.murakumostream.api.MurakumoStreamService
import com.prototype.murakumo.api.MurakumoService
import com.softwaremill.macwire._

class MurakumoStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new MurakumoStreamApplication(context) {
      override def serviceLocator: NoServiceLocator.type = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new MurakumoStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[MurakumoStreamService])
}

abstract class MurakumoStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[MurakumoStreamService](wire[MurakumoStreamServiceImpl])

  // Bind the MurakumoService client
  lazy val murakumoService: MurakumoService = serviceClient.implement[MurakumoService]
}
