package com.prototype.auth.impl

import org.scalatest.flatspec.AnyFlatSpec

class ValidationSpec extends AnyFlatSpec {

  "username" should "alpha-numeric only, maximum 36 characters, and not be empty" in {
    assert( Validation.validateUsername( "hello" )   == true )
    assert( Validation.validateUsername( "admin" )   == true )
    assert( Validation.validateUsername( "gayatri" ) == true )
    assert( Validation.validateUsername( "qiuyang" ) == true )
    assert( Validation.validateUsername( "Qiuyang" ) == true )
    assert( Validation.validateUsername( "123" )     == true )
    assert( Validation.validateUsername( "" )        == false )
    assert( Validation.validateUsername( "*&^%^$&" ) == false )
    assert( Validation.validateUsername( "adminadminadminadminadminadminadminaaadsfsdg" ) == false )
  }

  "Password" should "minimum 8 characters" in {
    assert( Validation.validatePassword( "hello" )   == false )
    assert( Validation.validatePassword( "admin" )   == false )
    assert( Validation.validatePassword( "gayatri" ) == false )
    assert( Validation.validatePassword( "qiuyang" ) == false )
    assert( Validation.validatePassword( "Qiuyang" ) == false )
    assert( Validation.validatePassword( "123" )     == false )
    assert( Validation.validatePassword( "" )        == false )
    assert( Validation.validatePassword( "*&^%^$&" ) == false )
    assert( Validation.validatePassword( "1234567" ) == false )
    assert( Validation.validatePassword( "adminadminadminadminadminadminadminaaadsfsdg" ) == true )
    assert( Validation.validatePassword( "12345678" ) == true )
    assert( Validation.validatePassword( "        " ) == false )
  }

} 
