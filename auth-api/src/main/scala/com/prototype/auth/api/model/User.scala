package com.prototype.auth.api.model

case class User(principal: String, roles: Set[Role])
