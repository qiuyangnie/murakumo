package com.prototype.auth.impl

import com.github.t3hnar.bcrypt._

import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Try

class StringHashSpec extends AnyFlatSpec {

  "Passwords" should "never be stored in database" in {

    val username: String = "admin"
    val password: String = "farm1990M0O"

    val passwordHash: Try[String] = StringHash.hashing(password)
    println(passwordHash.get)


    val passwordAgainst: String = "farm1990M0O"
    val result: Try[Boolean] = passwordAgainst.isBcryptedSafeBounded(passwordHash.get)
    println(result.get)

    val passwordAgainst1: String = "farm1990M0"
    val result1: Try[Boolean] = passwordAgainst1.isBcryptedSafeBounded(passwordHash.get)
    println(result1.get)
    
  }

}
