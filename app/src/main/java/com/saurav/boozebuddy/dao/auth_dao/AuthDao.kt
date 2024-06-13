package com.saurav.boozebuddy.dao.auth_dao

interface AuthDao {
     fun userLogin(email: String, password: String, callback: (Boolean, String?) -> Unit)

     fun userSignUp(name:String, email:String, password:String, callback: (Boolean, String?) -> Unit)

     fun userLogout()

     fun userAlreadyLoggedIn() : Boolean
}