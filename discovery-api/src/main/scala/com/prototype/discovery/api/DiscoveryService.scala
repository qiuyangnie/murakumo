package com.prototype.discovery.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, Descriptor, ServiceAcl, ServiceCall}

trait DiscoveryService extends Service {

  override def descriptor: Descriptor = {
    import Service._
    named("discovery")
      .withCalls(
        pathCall("/discovery/produce", () => produceMessage())
      )
      .withAutoAcl(true)
      .withAcls(
        ServiceAcl.forMethodAndPathRegex(Method.OPTIONS, "/.*")
      )
  }

  def produceMessage(): ServiceCall[NotUsed, NotUsed]

}
