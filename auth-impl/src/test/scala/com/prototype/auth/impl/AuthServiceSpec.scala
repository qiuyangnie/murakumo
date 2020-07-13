package com.prototype.auth.impl

import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest

import org.scalatest.{AsyncWordSpec, Matchers, BeforeAndAfterAll}

import java.util.regex.Pattern

import com.prototype.auth.api.AuthService
import com.prototype.auth.api.model.TokenRequest

class AuthServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  private val server = ServiceTest.startServer(
    ServiceTest.defaultSetup
  ) { ctx => 
    new AuthServiceApplication(ctx) with LocalServiceLocator
  }
  private val client: AuthService = server.serviceClient.implement[AuthService]

  override protected def afterAll(): Unit = server.stop()
  "auth service" should {
    "produce token" in {
      client.token().invoke(TokenRequest("admin", "admin")).map { answer =>
        val header = answer.split(Pattern.quote(".")).head
        header should ===("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9")
      }
    }
  }

}
