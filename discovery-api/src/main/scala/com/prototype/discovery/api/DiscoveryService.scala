package com.prototype.discovery.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, Descriptor, ServiceAcl, ServiceCall}
import com.lightbend.lagom.scaladsl.api.broker.Topic

import com.prototype.discovery.api.model.File

object DiscoveryService {
  val file_TOPIC_NAME = "file"
}

trait DiscoveryService extends Service {

  override def descriptor: Descriptor = {
    import Service._
    named("discovery")
      .withCalls(
        pathCall("/discovery/produce", () => produceMessage())
      )
      .withTopics(
        topic(DiscoveryService.file_TOPIC_NAME, fileTopic)
      )
      .withAutoAcl(true)
      .withAcls(
        ServiceAcl.forMethodAndPathRegex(Method.OPTIONS, "/.*")
      )
  }

  // The topic handle:
  def fileTopic(): Topic[File]

  // The call handle:
  def produceMessage(): ServiceCall[NotUsed, NotUsed]

}
