package com.example.stomone

import java.io.Serializable

data class LoginItem(
    var surnameFilds : String,
    var nameFilds : String,
    var patronymicFilds : String,
    var passwordFilds : String
) : Serializable
