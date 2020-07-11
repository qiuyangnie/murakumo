package com.prototype.auth.impl

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import com.prototype.auth.api.model.User
import com.prototype.auth.api.model.Role

object UserStorageImpl extends UserStorage {

  // TODO: add database for user authentication.
  override def getUser(username: String, password: String): Future[Option[User]] = 
    Future {
      (username, password) match {
        case ("head", "head")         => Some(User(username, Set(Role.Head)))
        case ("manager", "manager")   => Some(User(username, Set(Role.Manager)))
        case ("admin", "admin")       => Some(User(username, Set(Role.Admin)))
        case ("engineer", "engineer") => Some(User(username, Set(Role.Engineer)))
        case ("customer", "customer") => Some(User(username, Set(Role.Customer)))
        case _                        => None
      }
    }

}
