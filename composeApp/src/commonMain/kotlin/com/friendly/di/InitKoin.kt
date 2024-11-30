package com.friendly.di

import io.github.jan.supabase.SupabaseClient
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin{
        modules(appModule)
        modules(supabaseModule)
    }
}