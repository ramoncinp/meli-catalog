package com.ramoncinp.melicatalog.di

import com.ramoncinp.melicatalog.data.repository.MeLiRepository
import com.ramoncinp.melicatalog.data.repository.MeLiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMeLiRepository(meLiRepositoryImpl: MeLiRepositoryImpl): MeLiRepository
}
