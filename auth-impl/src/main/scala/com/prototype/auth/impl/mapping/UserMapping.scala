package com.prototype.auth.impl.mapping

import play.api.Configuration
import slick.jdbc.JdbcBackend.Database
import slick.lifted.Tag
import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext

// Functional Relational Mapping
class UserMapping(db: Database, config: Configuration)(implicit ex: ExecutionContext) {

  private final class Users(tag: Tag) extends Table[(Int, String, String)](tag, "USERS") {
    def id: Rep[Int]          = column[Int]("ID", O.PrimaryKey)
    def username: Rep[String] = column[String]("USERNAME")
    def password: Rep[String] = column[String]("PASSWORD")
    override def * = (id, username, password)
  }

  private val users: TableQuery[UserMapping.this.Users] = TableQuery[Users]

  def setup() = db.run(DBIO.seq(users.schema.create))

}
