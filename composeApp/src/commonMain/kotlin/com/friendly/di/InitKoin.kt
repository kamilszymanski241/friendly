package com.friendly.di

import com.friendly.nativeModule
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin{
        modules(appModule)
        modules(supabaseModule)
        modules(nativeModule)
    }
}