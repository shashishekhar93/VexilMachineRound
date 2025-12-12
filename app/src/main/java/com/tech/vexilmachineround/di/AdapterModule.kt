package com.tech.vexilmachineround.di

import android.app.Activity
import com.tech.vexilmachineround.adapter.HomePagerAdapter
import com.tech.vexilmachineround.adapter.InspectionAdapter
import com.tech.vexilmachineround.presentation.HomeActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class AdapterModule {

    @ActivityScoped
    @Provides
    fun provideHomePagerAdapter(activity: Activity): HomePagerAdapter {
        return HomePagerAdapter(activity as HomeActivity)
    }

    @ActivityScoped
    @Provides
    fun provideInspectionAdapter(): InspectionAdapter{
        return InspectionAdapter()
    }
}