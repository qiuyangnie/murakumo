package com.prototype.auth.impl.mapping

import play.api.Configuration
import slick.jdbc.JdbcBackend.Database
import slick.lifted.Tag
import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext

class RoleMapping(db: Database, config: Configuration)(implicit ex: ExecutionContext) {

  private final class Roles(tag: Tag) extends Table[(Int, String)](tag, "ROLES") {
    def id: Rep[Int]          = column[Int]("ID", O.PrimaryKey)
    def function: Rep[String] = column[String]("Function")
    override def * = (id, function)
  }

  private val roles: TableQuery[RoleMapping.this.Roles] = TableQuery[Roles]

  def setup() = db.run(DBIO.seq(roles.schema.create))

}
