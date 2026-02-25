package com.example.themealapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themealapp.remote.RetrofitClient
import com.example.themealapp.repository.MealRepository
import kotlinx.coroutines.launch

//Manual constructor injection
//TODO: Hilt DI
class MealViewModel(private val mealRepository: MealRepository = MealRepository()): ViewModel() {

    private val _mealState = MutableLiveData<MealsState>(MealsState.Loading)
    val mealState: LiveData<MealsState> = _mealState

    //2 ways
    //LaunchedEffect
    //init
    init {
        fetchMeals()
    }

    private fun fetchMeals() {
        viewModelScope.launch {
            _mealState.value = MealsState.Loading
            val result = mealRepository.getMeals()
            _mealState.value = if(result.isSuccess){
                val meals = result.getOrNull() ?: emptyList()
                MealsState.Success(meals)
            } else{
                MealsState.Error(result.exceptionOrNull() ?.message ?: "Unknown error")
            }
        }
    }
}