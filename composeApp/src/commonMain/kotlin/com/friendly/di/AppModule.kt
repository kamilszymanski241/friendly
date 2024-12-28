package com.friendly.di

import com.friendly.managers.IRegistrationManager
import com.friendly.repositories.AuthRepository
import com.friendly.repositories.EventRepository
import com.friendly.repositories.EventUserRepository
import com.friendly.repositories.IAuthRepository
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IEventUserRepository
import com.friendly.repositories.IStorageRepository
import com.friendly.repositories.IUserDetailsRepository
import com.friendly.repositories.StorageRepository
import com.friendly.repositories.UserDetailsRepository
import com.friendly.managers.ISessionManager
import com.friendly.managers.RegistrationManager
import com.friendly.managers.SessionManager
import com.friendly.viewModels.AppSettingsScreenViewModel
import com.friendly.viewModels.DiscoverScreenViewModel
import com.friendly.viewModels.EventDetailsScreenViewModel
import com.friendly.viewModels.FillUserDetailsViewModel
import com.friendly.viewModels.HomeScreenTopBarViewModel
import com.friendly.viewModels.RegisterEmailAndPasswordViewModel
import com.friendly.viewModels.SignInViewModel
import com.friendly.viewModels.SignUpViewModel
import com.friendly.viewModels.UpcomingEventsScreenViewModel
import com.friendly.viewModels.UploadProfilePictureViewModel
import com.friendly.viewModels.UserProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    single<IEventRepository> {EventRepository()}
    single<IAuthRepository> {AuthRepository()}
    single<IStorageRepository>{StorageRepository()}
    single<IUserDetailsRepository>{UserDetailsRepository()}
    single<IEventUserRepository>{EventUserRepository()}

    single<ISessionManager>{SessionManager()}
    single<IRegistrationManager>{ RegistrationManager() }

    viewModelOf(::DiscoverScreenViewModel)
    viewModelOf(::UpcomingEventsScreenViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::HomeScreenTopBarViewModel)
    viewModelOf(::FillUserDetailsViewModel)
    viewModelOf(::UploadProfilePictureViewModel)
    viewModelOf(::RegisterEmailAndPasswordViewModel)
    viewModelOf(::UserProfileViewModel)
    viewModel{(eventId: String)->EventDetailsScreenViewModel(eventId)}
    viewModelOf(::AppSettingsScreenViewModel)
}