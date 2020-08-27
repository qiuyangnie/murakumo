package com.prototype.auth.impl.mapping

import play.api.Configuration
import slick.jdbc.JdbcBackend.Database
import slick.lifted.Tag
import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext

class Mapping(db: Database, config: Configuration)(implicit ex: ExecutionContext) {

  // Table rows:
  private final class Users(tag: Tag) extends Table[(Int, Int, String, String)](tag, "USERS") {
    def id: Rep[Int]          = column[Int]("ID", O.PrimaryKey, O.Unique, O.AutoInc)
    def roleID: Rep[Int]      = column[Int]("ROLE_ID")
    def username: Rep[String] = column[String]("USERNAME", O.Unique)
    def password: Rep[String] = column[String]("PASSWORD", O.Unique)
    def role = foreignKey("ROLE_FK", roleID, roles)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    override def * = (id, roleID, username, password)
  }

  private final class Roles(tag: Tag) extends Table[(Int, String)](tag, "ROLES") {
    def id: Rep[Int]      = column[Int]("ROLE_ID", O.PrimaryKey, O.Unique, O.AutoInc)
    def role: Rep[String] = column[String]("ROLE", O.Unique)
    override def * = (id, role)
  }

  // Table query:
  private val users: TableQuery[Mapping.this.Users] = TableQuery[Users]
  private val roles: TableQuery[Mapping.this.Roles] = TableQuery[Roles]

  def setup() = db.run(DBIO.seq(
    (users.schema ++ roles.schema).create
  ))

}
