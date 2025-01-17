package com.friendly

import android.app.Application
import com.friendly.di.initKoin
import org.koin.android.ext.koin.androidContext

class FriendlyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@FriendlyApplication)
        }
    }
}