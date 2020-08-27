package com.prototype.auth.impl.mapping

import play.api.Configuration
import slick.jdbc.JdbcBackend.Database
import slick.lifted.Tag
import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext

class RoleMapping(db: Database, config: Configuration)(implicit ex: ExecutionContext) {

  private final class Roles(tag: Tag) extends Table[(Int, String)](tag, "ROLES") {
    def id: Rep[Int]          = column[Int]("ID", O.PrimaryKey, O.Unique, O.AutoInc)
    def role: Rep[String] = column[String]("ROLE", O.Unique)
    override def * = (id, role)
  }

  private val roles: TableQuery[RoleMapping.this.Roles] = TableQuery[Roles]

  def setup() = db.run(DBIO.seq(roles.schema.create))

}
