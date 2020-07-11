package com.prototype.auth.impl

import scala.concurrent.Future

import com.prototype.auth.api.model.User

trait UserStorage {
  /** Gets a corresponding user with a given username and password.
   *
   *  @param username username
   *  @param password password for authentication
   *  @return a new User instance.
   */
  def getUser(username: String, password: String): Future[Option[User]]
}
