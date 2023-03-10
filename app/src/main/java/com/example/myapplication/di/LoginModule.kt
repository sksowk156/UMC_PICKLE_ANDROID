package com.example.myapplication.di

import com.example.myapplication.data.remote.LoginService
import com.example.myapplication.repository.LoginRepository
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

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

    @Singleton
    @Provides
    fun provideRetrofitInstance(
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
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginService: LoginService): LoginRepository {
        return  LoginRepository(loginService)
    }

//    class AppInterceptor : Interceptor {
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val builder: Request.Builder = chain.request().newBuilder()
//            val jwtToken: String? = Utils.sharedPreferencesmanager.getJwt(Utils.X_ACCESS_TOKEN)
//            // 서버로 부터 발급 받은 토큰 값이 preference에 저장되어 있다면 retrofit 쓸 때마다 자동으로 헤더에 넣어준다.
//            jwtToken?.let {
//                builder.addHeader("Authorization", jwtToken ?: "")
//            }
//            return chain.proceed(builder.build())
//        }
//    }
}