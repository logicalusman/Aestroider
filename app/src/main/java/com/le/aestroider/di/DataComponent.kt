package com.le.aestroider.di

import com.le.aestroider.feature.home.ui.HomeFragment
import com.le.aestroider.feature.home.viewmodel.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, ViewModelModule::class))
interface DataComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(homeViewModel: HomeViewModel)
}