package com.prototype.auth.impl

object Validation {

  def validateUsername(username: String): Boolean = {
    val pattern: String = "\\w{1,36}"
    username.matches( pattern )
  }

  def validatePassword(password: String): Boolean = {
    val pattern: String = raw".{8,}"
    password.matches( pattern )
  }

} 
