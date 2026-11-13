package com.lavinef.motorevents.data.f1.jolpica

import com.lavinef.motorevents.data.f1.jolpica.service.JolpicaF1ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JolpicaNetworkModule {

    private const val BASE_URL = "https://api.jolpi.ca/ergast/f1/"

    @Provides
    @Singleton
    fun provideJolpicaRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideJolpicaF1ApiService(retrofit: Retrofit): JolpicaF1ApiService {
        return retrofit.create(JolpicaF1ApiService::class.java)
    }
}
