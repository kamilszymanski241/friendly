package com.friendly.di

import com.friendly.repositories.AuthRepository
import com.friendly.repositories.EventRepository
import com.friendly.repositories.IAuthRepository
import com.friendly.repositories.IEventRepository
import com.friendly.viewModels.EventViewModel
import com.friendly.viewModels.SignUpViewModel
import com.friendly.viewModels.SignInViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    single<IEventRepository> {EventRepository()}
    single<IAuthRepository> {AuthRepository()}
    viewModelOf(::EventViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
}