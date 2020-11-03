package com.prototype.auth.impl.mapping

import play.api.Configuration
import slick.jdbc.JdbcBackend.Database
import slick.lifted.Tag
import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext

class Mapping(db: Database, config: Configuration)(implicit ex: ExecutionContext) {

  // Definition of the USERS table
  private final class Users(tag: Tag) extends Table[(Int, String, String)](tag, "USERS") {
    def id: Rep[Int]          = column[Int]("USER_ID", O.PrimaryKey, O.Unique, O.AutoInc)
    def username: Rep[String] = column[String]("USERNAME", O.Unique)
    def password: Rep[String] = column[String]("PASSWORD", O.Unique)
    override def * = (id, username, password)
  }

  // Definition of the ROLES table
  private final class Roles(tag: Tag) extends Table[(Int, String)](tag, "ROLES") {
    def id: Rep[Int]      = column[Int]("ROLE_ID", O.PrimaryKey, O.Unique, O.AutoInc)
    def role: Rep[String] = column[String]("ROLE", O.Unique)
    override def * = (id, role)
  }

  // Definition of the PERMISSIONS table
  private final class Permissions(tag: Tag) extends Table[(Int, String)](tag, "PERMISSIONS") {
    def id: Rep[Int]            = column[Int]("PERMISSION_ID", O.PrimaryKey, O.Unique, O.AutoInc)
    def permission: Rep[String] = column[String]("PERMISSION", O.Unique)
    override def * = (id ,permission)
  }

  // Definition of the ASSIGNMENTS table
  private final class Assignments(tag: Tag) extends Table[(Int, Int)](tag, "ASSIGNMENTS") {
    def userID: Rep[Int] = column[Int]("USER_ID")
    def roleID: Rep[Int] = column[Int]("ROLE_ID")
    def user = foreignKey("USER_FK", userID, users)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def role = foreignKey("ROLE_FK", roleID, roles)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    override def * = (userID, roleID)
  }

  // Definition of the AUTHORIZATIONS table
  private final class Authorizations(tag: Tag) extends Table[(Int, Int)](tag, "AUTHORIZATIONS") {
    def roleID: Rep[Int]       = column[Int]("ROLE_ID")
    def permissionID: Rep[Int] = column[Int]("PERMISSION_ID")
    def role       = foreignKey("ROLE_FK", roleID, roles)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def permission = foreignKey("PERMISSION_FK", permissionID, permissions)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    override def * = (roleID, permissionID)
  }

  // Table queries:
  private val users: TableQuery[Mapping.this.Users] = TableQuery[Users]
  private val roles: TableQuery[Mapping.this.Roles] = TableQuery[Roles]
  private val permissions: TableQuery[Mapping.this.Permissions] = TableQuery[Permissions]
  private val assignments: TableQuery[Mapping.this.Assignments] = TableQuery[Assignments]
  private val authorizations: TableQuery[Mapping.this.Authorizations] = TableQuery[Authorizations]

  def setup() = db.run(DBIO.seq(
    (users.schema ++ roles.schema ++ permissions.schema ++ assignments.schema ++ authorizations.schema).create,

    roles.map(r => (r.role)) ++= Seq(
      ("Administrator"),
      ("DataQA"),
      ("SRE"),
      ("Auditor")
    ),

    users.map(u => (u.username, u.password)) += ("admin", "123")

  ))

}
