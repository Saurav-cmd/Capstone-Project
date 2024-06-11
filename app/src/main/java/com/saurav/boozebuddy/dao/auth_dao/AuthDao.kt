package com.saurav.boozebuddy.dao.auth_dao

interface AuthDao {
     fun userLogin(email: String, password: String, callback: (Boolean, String?) -> Unit)

     fun userSignUp()

     fun userLogout()

     fun userAlreadyLoggedIn() : Boolean
}