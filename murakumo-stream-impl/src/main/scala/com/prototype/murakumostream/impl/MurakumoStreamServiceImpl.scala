package com.prototype.murakumostream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.prototype.murakumostream.api.MurakumoStreamService
import com.prototype.murakumo.api.MurakumoService

import scala.concurrent.Future

/**
  * Implementation of the MurakumoStreamService.
  */
class MurakumoStreamServiceImpl(murakumoService: MurakumoService) extends MurakumoStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(murakumoService.hello(_).invoke()))
  }
}
