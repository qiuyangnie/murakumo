package com.prototype.auth.api.utilities

import pdi.jwt.{Jwt, JwtAlgorithm}

object JwtUtility {

  private val secretKey = "secretKey"

  def createToken(payload: String): String = 
    Jwt.encode(payload, secretKey, JwtAlgorithm.HS256)

  def isValidToken(token: String): Boolean = 
    Jwt.isValid(token, secretKey, Seq(JwtAlgorithm.HS256))

  def decodeToken(token: String): Option[String] =
    Jwt.decode(token, secretKey, Seq(JwtAlgorithm.HS256)).map(_.toJson).toOption

}
