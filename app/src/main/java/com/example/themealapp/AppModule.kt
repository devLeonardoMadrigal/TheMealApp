package com.example.themealapp

import com.example.themealapp.api.MealApi
import com.example.themealapp.repository.MealRepository
import com.example.themealapp.viewmodel.MealViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

val appModule = module {

    //////////////////////////////////////////////////////////////
    //Retrofit instance, equivalent to remote/RetrofitClient
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(MealApi::class.java)
    }
    //////////////////////////////////////////////////////////////

    //Repository
    single {
        MealRepository(get())
    }

    //viewModel
    viewModel{
        MealViewModel(get())
    }
}