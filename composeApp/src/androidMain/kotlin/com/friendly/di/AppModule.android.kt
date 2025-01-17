package com.friendly.di

import com.friendly.helpers.LocationAndGeocodingHelper
import com.friendly.helpers.PlacesClientProvider
import com.friendly.helpers.PlacesHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single{ PlacesClientProvider(androidApplication()) }
        single{LocationAndGeocodingHelper(androidApplication())}
        single{ PlacesHelper() }
    }