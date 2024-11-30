package com.friendly.di

import com.friendly.repositories.EventRepository
import com.friendly.repositories.IEventRepository
import com.friendly.viewModels.EventViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    //single<IEventsDataService> { EventsDataService() }
    single<IEventRepository> {EventRepository()}
    viewModelOf(::EventViewModel)
}