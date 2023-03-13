package com.example.myapplication.di

import android.content.Context
import android.util.Log
import com.example.myapplication.data.remote.*
import com.example.myapplication.repository.*
import com.example.myapplication.widget.utils.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context) : SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginService: LoginService): LoginRepository {
        return  LoginRepository(loginService)
    }

    @Singleton
    @Provides
    fun provideHomeRepository(homeService: HomeService): HomeRepository {
        return  HomeRepository(homeService)
    }

    @Singleton
    @Provides
    fun provideDressRepository(dressService: DressService): DressRepository {
        return  DressRepository(dressService)
    }

    @Singleton
    @Provides
    fun provideStoreRepository(storeService: StoreService): StoreRepository {
        return  StoreRepository(storeService)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userService: UserService): UserRepository {
        return  UserRepository(userService)
    }
}