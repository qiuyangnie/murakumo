package com.prototype.auth.impl

import com.github.t3hnar.bcrypt._

import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Try

class StringHashSpec extends AnyFlatSpec {

  "Passwords" should "never be stored in database" in {

    val username: String = "admin"
    val password: String = "farm1990M0O"

    val passwordHash0: Try[String] = StringHash.hashing(password)
    val passwordHash1: Try[String] = StringHash.hashing(password)
    val passwordHash2: Try[String] = StringHash.hashing(password)
    val passwordHash3: Try[String] = StringHash.hashing(password)

    println(passwordHash0.get)
    println(passwordHash1.get)
    println(passwordHash2.get)
    println(passwordHash3.get)

    val passwordAgainst0: String = "farm1990M0O"
    val result0: Try[Boolean] = passwordAgainst0.isBcryptedSafeBounded(passwordHash2.get)
    println(result0.get)

  }

}
