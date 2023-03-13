package com.example.myapplication.di

import com.example.myapplication.data.remote.*
import com.example.myapplication.widget.utils.SharedPreferencesManager
import com.example.myapplication.widget.utils.Utils.BASE_URL
import com.example.myapplication.widget.utils.XAccessTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideSessionManager(sharedPreferences: SharedPreferencesManager): XAccessTokenInterceptor =
        XAccessTokenInterceptor(sharedPreferences)

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: XAccessTokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor) // preference에 저장된 jwt 토큰을 헤더에 자동으로 입력
            .build()
    }

    @Provides
    @Singleton
    @LOGIN
    fun provideLoginRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    @HOME
    fun provideHomeRetrofitInstance2(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    @DRESS
    fun provideDressRetrofitInstance3(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    @STORE
    fun provideStoreRetrofitInstance4(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    @USER
    fun provideUserRetrofitInstance5(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Provides
    @Singleton
    fun provideLoginService(@LOGIN retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeService(@HOME retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }

    @Provides
    @Singleton
    fun provideDressService(@HOME retrofit: Retrofit): DressService {
        return retrofit.create(DressService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoreService(@HOME retrofit: Retrofit): StoreService {
        return retrofit.create(StoreService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(@HOME retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LOGIN

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HOME

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class STORE

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DRESS

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class USER
}
