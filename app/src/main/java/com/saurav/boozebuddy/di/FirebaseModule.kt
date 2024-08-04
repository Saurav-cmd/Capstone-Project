package com.saurav.boozebuddy.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.saurav.boozebuddy.api_services.FirebaseHelper
import com.saurav.boozebuddy.api_services.FirestoreHelper
import com.saurav.boozebuddy.app_interface.places_interface.PlacesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideRealtimeFirebaseDB(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideFirestoreHelper(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth,
        realTimeDB: FirebaseDatabase
    ): FirestoreHelper = FirestoreHelper(firestore, auth, realTimeDB)

    //his method tells Hilt to create a FirebaseHelper instance by
    // passing the FirebaseAuth instance provided by provideFirebaseAuth
    @Provides
    @Singleton
    fun provideFirebaseHelper(auth: FirebaseAuth, firestore: FirebaseFirestore): FirebaseHelper =
        FirebaseHelper(auth, firestore)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providePlacesApiService(retrofit: Retrofit): PlacesApiService =
        retrofit.create(PlacesApiService::class.java)
}