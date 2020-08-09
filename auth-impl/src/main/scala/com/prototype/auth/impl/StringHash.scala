package com.prototype.auth.impl

import com.github.t3hnar.bcrypt._

import scala.util.Try

object StringHash {
  
  def hashing(string: String): Try[String] = string.bcryptSafeBounded

}
