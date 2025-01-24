package com.friendly.di

import com.friendly.managers.IRegistrationManager
import com.friendly.managers.ISessionManager
import com.friendly.managers.RegistrationManager
import com.friendly.managers.SessionManager
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
import com.friendly.viewModels.AppSettingsScreenViewModel
import com.friendly.viewModels.EventDetailsScreenViewModel
import com.friendly.viewModels.SearchLocationViewModel
import com.friendly.viewModels.UserProfileViewModel
import com.friendly.viewModels.home.DiscoverScreenViewModel
import com.friendly.viewModels.home.HomeScreenTopBarViewModel
import com.friendly.viewModels.home.HomeScreenViewModel
import com.friendly.viewModels.home.MyEventsScreenViewModel
import com.friendly.viewModels.home.UpcomingEventsScreenViewModel
import com.friendly.viewModels.signInSignUp.FillUserDetailsViewModel
import com.friendly.viewModels.signInSignUp.RegisterEmailAndPasswordViewModel
import com.friendly.viewModels.signInSignUp.SignInViewModel
import com.friendly.viewModels.signInSignUp.SignUpViewModel
import com.friendly.viewModels.signInSignUp.UploadProfilePictureViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    single<IEventRepository> {EventRepository()}
    single<IAuthRepository> {AuthRepository()}
    single<IStorageRepository>{StorageRepository()}
    single<IUserDetailsRepository>{UserDetailsRepository()}
    single<IEventUserRepository>{EventUserRepository()}

    single<ISessionManager>{SessionManager()}
    single<IRegistrationManager>{ RegistrationManager() }

    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::DiscoverScreenViewModel)
    viewModelOf(::UpcomingEventsScreenViewModel)
    viewModelOf(::MyEventsScreenViewModel)

    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::HomeScreenTopBarViewModel)
    viewModelOf(::FillUserDetailsViewModel)
    viewModelOf(::UploadProfilePictureViewModel)
    viewModelOf(::RegisterEmailAndPasswordViewModel)
    viewModel{(userId: String)->UserProfileViewModel(userId)}
    viewModel{(eventId: String)->EventDetailsScreenViewModel(eventId)}
    viewModelOf(::AppSettingsScreenViewModel)
    viewModelOf(::SearchLocationViewModel)
}