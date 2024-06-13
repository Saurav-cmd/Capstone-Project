package com.saurav.boozebuddy.api_services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHelper(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {

    //Function to login user using email and password
    fun signInWithEmailAndPassword(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true, null)
                    } else {
                        val exception = task.exception
                        callback(false, ErrorHandler.getErrorMessage(exception as Exception))
                    }
                }
        } catch (e: Exception) {
            callback(false, ErrorHandler.getErrorMessage(e))
        }
    }

    fun signUpWithEmailAndPassword(name:String, email:String, password:String, callback: (Boolean, String?) -> Unit){
        try{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // User created successfully, now store additional user data in Firestore
                        val userId = auth.currentUser?.uid
                        val user = hashMapOf(
                            "name" to name,
                            "email" to email
                        )

                        if (userId != null) {
                            db.collection("users").document(userId)
                                .set(user)
                                .addOnSuccessListener {
                                    signOutUser()
                                    callback(true, null)
                                }
                                .addOnFailureListener { e ->
                                    callback(false, e.message)
                                }
                        } else {
                            callback(false, "User ID is null")
                        }
                    } else {
                        callback(false, task.exception?.message)
                    }
                }
                .addOnFailureListener { e ->
                    callback(false, e.message)
                }
        }catch (e: Exception){
            callback(false, ErrorHandler.getErrorMessage(e))
        }
    }

    //Function to logout user
    fun signOutUser() {
        try {
            auth.signOut()
        } catch (e: Exception) {
           ErrorHandler.getErrorMessage(e)
        }
    }

    //Function to check if the user has already logged in or not
    fun userAlreadyLoggedIn(): Boolean {
        return try {
            auth.currentUser != null
        } catch (e: java.lang.Exception) {
            ErrorHandler.getErrorMessage(e)
            false
        }
    }
}
