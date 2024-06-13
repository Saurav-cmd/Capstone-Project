package com.saurav.boozebuddy.models

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: String = "",
    val name: String = "",
    val email: String = ""
)
