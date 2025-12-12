package com.tech.vexilmachineround.di

import android.content.Context
import com.tech.vexilmachineround.repository.JsonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JsonModule {
    @Provides
    @Singleton
    fun provideJsonRepository(@ApplicationContext context: Context): JsonRepository {
        return JsonRepository(context)
    }


}