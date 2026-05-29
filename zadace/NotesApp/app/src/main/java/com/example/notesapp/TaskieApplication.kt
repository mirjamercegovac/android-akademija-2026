package com.example.notesapp

import android.app.Application
import com.example.notesapp.data.local.di.localModule
import com.example.notesapp.data.network.di.networkModule
import com.example.notesapp.data.repository.di.repositoryModule
import com.example.notesapp.di.loggingModule
import com.example.notesapp.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TaskieApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TaskieApplication)
            modules(
                loggingModule,
                localModule,
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}