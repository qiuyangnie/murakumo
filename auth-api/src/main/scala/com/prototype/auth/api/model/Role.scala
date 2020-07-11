package com.prototype.auth.api.model

sealed trait Role

object Role {

  case object Head extends Role

  case object Manager extends Role

  case object Admin extends Role

  case object Engineer extends Role

  case object Customer extends Role

}
