package com.prototype.auth.impl

import com.prototype.auth.api.model.User

import scala.concurrent.Future

trait UserStorage {
  /** Gets a corresponding user with a given username and password.
   *
   *  @param username username
   *  @param password password for authentication
   *  @return a new User instance.
   */
  def getUser(username: String, password: String): Future[Option[User]]
}
