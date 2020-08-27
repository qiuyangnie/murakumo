package com.prototype.auth.impl.mapping

import play.api.Configuration
import slick.jdbc.JdbcBackend.Database
import slick.lifted.Tag
import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext

class Mapping(db: Database, config: Configuration)(implicit ex: ExecutionContext) {

  // Table rows:
  private final class Users(tag: Tag) extends Table[(Int, String, String)](tag, "USERS") {
    def id: Rep[Int]          = column[Int]("ID", O.PrimaryKey, O.Unique, O.AutoInc)
    def username: Rep[String] = column[String]("USERNAME", O.Unique)
    def password: Rep[String] = column[String]("PASSWORD", O.Unique)
    override def * = (id, username, password)
  }

  private final class Roles(tag: Tag) extends Table[(Int, String)](tag, "ROLES") {
    def id: Rep[Int]      = column[Int]("ID", O.PrimaryKey, O.Unique, O.AutoInc)
    def role: Rep[String] = column[String]("ROLE", O.Unique)
    override def * = (id, role)
  }

  private final class Assignments(tag: Tag) extends Table[((Int, Int))](tag, "ASSIGNMENTS") {
    def userID: Rep[Int] = column[Int]("USER_ID")
    def roleID: Rep[Int] = column[Int]("ROLE_ID")
    def user = foreignKey("USER_FK", userID, users)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def role = foreignKey("ROLE_FK", roleID, roles)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    override def * = (userID, roleID) 
  }

  // Table query:
  private val users: TableQuery[Mapping.this.Users] = TableQuery[Users]
  private val roles: TableQuery[Mapping.this.Roles] = TableQuery[Roles]
  private val assignments: TableQuery[Mapping.this.Assignments] = TableQuery[Assignments]

  def setup() = db.run(DBIO.seq(
    (users.schema ++ roles.schema ++ assignments.schema).create
  ))

}
