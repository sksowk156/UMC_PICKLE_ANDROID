package com.example.myapplication.di

import android.content.Context
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
}