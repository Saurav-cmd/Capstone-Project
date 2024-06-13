package com.saurav.boozebuddy.impl.auth_impl

import com.saurav.boozebuddy.api_services.FirebaseHelper
import com.saurav.boozebuddy.dao.auth_dao.AuthDao
import javax.inject.Inject

//the @Inject annotation to tell Hilt that it needs an instance of FirebaseHelper to create an instance of AuthImpl
class AuthImpl @Inject constructor(private val firebaseHelper: FirebaseHelper): AuthDao {

    //Function to login user
    override fun userLogin(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseHelper.signInWithEmailAndPassword(email, password, callback)
    }

    //Function to signup user
    override fun userSignUp(
        name: String,
        email: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) {
        firebaseHelper.signUpWithEmailAndPassword(name, email,password ,callback)
    }



    override fun userLogout() {
        firebaseHelper.signOutUser()
    }

    override fun userAlreadyLoggedIn(): Boolean {
        return firebaseHelper.userAlreadyLoggedIn()
    }
}