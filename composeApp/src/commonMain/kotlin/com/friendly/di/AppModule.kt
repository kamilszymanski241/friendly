package com.friendly.di

import com.friendly.dataServices.EventsDataService
import com.friendly.dataServices.IEventsDataService
import com.friendly.httpClient
import org.koin.dsl.module


val appModule = module {
    single<IEventsDataService> { EventsDataService() }
}