package com.friendly.di

import com.friendly.repositories.AuthRepository
import com.friendly.repositories.EventRepository
import com.friendly.repositories.IAuthRepository
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IStorageRepository
import com.friendly.repositories.IUserDetailsRepository
import com.friendly.repositories.StorageRepository
import com.friendly.repositories.UserDetailsRepository
import com.friendly.session.ISessionManager
import com.friendly.session.SessionManager
import com.friendly.viewModels.UserDetailsViewModel
import com.friendly.viewModels.DiscoverScreenViewModel
import com.friendly.viewModels.SignUpViewModel
import com.friendly.viewModels.SignInViewModel
import com.friendly.viewModels.BarsViewModel
import com.friendly.viewModels.UserSessionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    single<IEventRepository> {EventRepository()}
    single<IAuthRepository> {AuthRepository()}
    single<IStorageRepository>{StorageRepository()}
    single<IUserDetailsRepository>{UserDetailsRepository()}
    single<ISessionManager>{SessionManager()}
    viewModelOf(::DiscoverScreenViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::BarsViewModel)
    viewModelOf(::UserSessionViewModel)
    viewModelOf(::UserDetailsViewModel)
}