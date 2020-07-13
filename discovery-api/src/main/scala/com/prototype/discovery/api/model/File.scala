package com.prototype.discovery.api.model

import java.time.ZonedDateTime
import java.util.UUID

import play.api.libs.json.{Format, Json}

case class File(id: String,
                source: String,
                path: String,
                fileType: String,
                createdAt: ZonedDateTime,
                lastModifiedAt: ZonedDateTime,
                contentLength: Long,
                configurationId: UUID)

object File {
  implicit val format: Format[File] = Json.format
}
