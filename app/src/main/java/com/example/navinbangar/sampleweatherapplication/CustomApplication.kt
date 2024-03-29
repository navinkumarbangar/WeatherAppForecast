package com.example.navinbangar.sampleweatherapplication

import android.app.Activity
import android.app.Application
import com.example.navinbangar.sampleweatherapplication.di.NetworksModule
import com.example.navinbangar.sampleweatherapplication.di.component.DaggerNetworkComponent
import com.example.navinbangar.sampleweatherapplication.di.component.NetworkComponent
import com.example.navinbangar.sampleweatherapplication.helper.Constants.Companion.weatherUrl
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Navin Bangar on 11/19/2019.
 * This is custom application class used in dagger implementation
 */


class CustomApplication : Application(), HasActivityInjector {
    private lateinit var networkComponent: NetworkComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        networkComponent = DaggerNetworkComponent.builder()
                .networksModule(NetworksModule(weatherUrl))
                .build()
    }

    fun getNetworkComponentFn(): NetworkComponent {
        return networkComponent
    }
}
