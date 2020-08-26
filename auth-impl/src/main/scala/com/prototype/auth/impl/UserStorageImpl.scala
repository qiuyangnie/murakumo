package com.prototype.auth.impl

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import com.prototype.auth.api.model.Permission
import com.prototype.auth.api.model.User

object UserStorageImpl extends UserStorage {

  // TODO: add database for user authentication.
  override def getUser(username: String, password: String): Future[Option[User]] = 
    Future {
      (username, password) match {
        case ("head", "head")         => Some(User(username, Set(Permission.CanAccessPII)))
        case ("manager", "manager")   => Some(User(username, Set(Permission.CanAccessPII)))
        case ("admin", "admin")       => Some(User(username, Set(Permission.CanAccessPII)))
        case ("engineer", "engineer") => Some(User(username, Set(Permission.CanAccessPII)))
        case ("customer", "customer") => Some(User(username, Set(Permission.CanAccessPII)))
        case _                        => None
      }
    }

}
