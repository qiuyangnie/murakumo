package com.prototype.auth.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.api.{Descriptor, ServiceLocator}
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer}
import com.softwaremill.macwire.wire
import com.prototype.auth.api.AuthService
import com.prototype.auth.impl.{AuthServiceImpl, UserStorage, UserStorageImpl}
import com.prototype.auth.impl.mapping.UserMapping
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.ws.ahc.AhcWSComponents
import slick.jdbc.JdbcBackend.Database
import scala.util.{Success, Failure}

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
    lazy val db: Database = Database.forConfig("db.default")
    lazy val log: Logger  = LoggerFactory.getLogger(classOf[AuthServiceImpl])
    lazy val userStorage: UserStorage = UserStorageImpl
    lazy val userMapping: UserMapping = wire[UserMapping]
    userMapping.setup().onComplete {
      case Success(_)         => log.info("USERS table has been successfully created")
      case Failure(exception) => log.error("USERS table could not be created", exception)
    }

    // Bind the service that this server provides
    override def lagomServer: LagomServer = serverFor[AuthService](wire[AuthServiceImpl])
}
