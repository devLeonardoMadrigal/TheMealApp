package com.example.themealapp.viewmodel

import com.example.themealapp.model.Meal

sealed class MealsState {
    object Loading : MealsState()
    data class Success(val meals:List<Meal>) : MealsState()
    data class Error(val message: String) : MealsState()
}