package com.ramoncinp.melicatalog.di

import com.ramoncinp.melicatalog.data.MeLiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideMeLiService(retrofit: Retrofit): MeLiService =
        retrofit.create(MeLiService::class.java)
}
