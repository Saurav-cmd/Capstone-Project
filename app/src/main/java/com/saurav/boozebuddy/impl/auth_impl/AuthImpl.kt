package com.saurav.boozebuddy.impl.auth_impl

import com.saurav.boozebuddy.api_services.FirebaseHelper
import com.saurav.boozebuddy.app_interface.auth_interface.AuthInterface
import javax.inject.Inject

class AuthImpl @Inject constructor(private val firebaseHelper: FirebaseHelper): AuthInterface {

    override fun userLogin(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseHelper.signInWithEmailAndPassword(email, password, callback)
    }

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
