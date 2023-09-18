package com.ramoncinp.melicatalog.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatchersModule {

    @Binds
    abstract fun bindDispatchers(defaultDispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}
