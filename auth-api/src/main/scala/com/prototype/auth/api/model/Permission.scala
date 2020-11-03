package com.prototype.auth.api.model

sealed trait Permission

object Permission {

  case object CanAccessPII extends Permission

  case object CanAccessInternal extends Permission

}
