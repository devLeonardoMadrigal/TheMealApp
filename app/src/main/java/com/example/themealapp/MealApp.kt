package com.example.themealapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MealApp : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MealApp)
            modules(appModule) // Load modules from AppModule app just created
        }
    }
}