package com.example.notesapp.data.network.di

import com.example.notesapp.data.network.RetrofitTaskieInstance
import com.example.notesapp.data.network.apiservice.RetrofitTaskieApiService
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val networkModule = module {

    single<RetrofitTaskieApiService> {
        RetrofitTaskieInstance.create(
            logger= get {parametersOf("RetrofitTaskieApiService")}
        )
    }
}