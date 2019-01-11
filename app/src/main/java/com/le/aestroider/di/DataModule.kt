package com.le.aestroider.di


import com.le.aestroider.data.AestroiderRepository
import com.le.aestroider.data.network.NetworkAdapter
import com.le.aestroider.data.network.NetworkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesAestroiderRepository() : AestroiderRepository {
        return AestroiderRepository(NetworkRepository(NetworkAdapter.nasaNeoApi()))
    }

}