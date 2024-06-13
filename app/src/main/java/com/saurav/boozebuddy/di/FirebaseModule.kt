package com.saurav.boozebuddy.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.saurav.boozebuddy.api_services.FirebaseHelper
import com.saurav.boozebuddy.api_services.FirestoreHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//Dependency Injection: FirebaseModule provides instances of
// FirebaseAuth and FirebaseHelper. AuthImpl uses these instances for authentication.
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    //This method tells Hilt how to create a FirebaseAuth instance.
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()



    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirestoreHelper(firestore: FirebaseFirestore, auth:FirebaseAuth): FirestoreHelper = FirestoreHelper(firestore, auth)

    //his method tells Hilt to create a FirebaseHelper instance by
    // passing the FirebaseAuth instance provided by provideFirebaseAuth
    @Provides
    @Singleton
    fun provideFirebaseHelper(auth: FirebaseAuth, firestore: FirebaseFirestore): FirebaseHelper = FirebaseHelper(auth, firestore)
}