package com.example.rainreminderweatherforecast.di.app

import com.example.rainreminderweatherforecast.repository.network.IWeatherAPIService
import com.example.rainreminderweatherforecast.repository.network.IWeatherAPIService.Companion.API_BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

   @Provides
   @Singleton
   fun provideApiService(retrofit: Retrofit): IWeatherAPIService =
      retrofit.create(IWeatherAPIService::class.java)

   @Provides
   @Singleton
   fun providesGsonRetrofit(
      okHttpClient: OkHttpClient,
      converterFactory: GsonConverterFactory
   ): Retrofit =
      Retrofit.Builder()
         .baseUrl(API_BASE_URL)
         .client(okHttpClient)
         .addConverterFactory(converterFactory)
         .build()

   @Provides
   @Singleton
   fun provideOkHttpClient(
      httpLoggingInterceptor: HttpLoggingInterceptor,
      interceptorQueryApiKey: Interceptor
   ): OkHttpClient =
      OkHttpClient.Builder()
         .addInterceptor(httpLoggingInterceptor)
         .addInterceptor(interceptorQueryApiKey)
         .build()

   @Provides
   @Singleton
   fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
      HttpLoggingInterceptor()
         .setLevel(HttpLoggingInterceptor.Level.BODY)

   @Provides
   @Singleton
   fun provideInterceptorAddQueryApiKey(): Interceptor =
      Interceptor() { chain ->
         var request = chain.request()
         val newHttpUrl = request.url.newBuilder()
            .addQueryParameter("key", IWeatherAPIService.API_KEY).build()
         request = request.newBuilder().url(newHttpUrl).build()
         return@Interceptor chain.proceed(request)
      }

   @Provides
   @Singleton
   fun provideJsonConverterFactory(): GsonConverterFactory =
      GsonConverterFactory.create()

}