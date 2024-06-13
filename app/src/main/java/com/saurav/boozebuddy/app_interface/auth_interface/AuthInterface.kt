package com.saurav.boozebuddy.app_interface.auth_interface

interface AuthInterface {
     fun userLogin(email: String, password: String, callback: (Boolean, String?) -> Unit)

     fun userSignUp(name:String, email:String, password:String, callback: (Boolean, String?) -> Unit)

     fun userLogout()

     fun userAlreadyLoggedIn() : Boolean
}