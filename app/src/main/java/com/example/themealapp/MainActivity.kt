package com.example.themealapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.themealapp.model.Meal
import com.example.themealapp.remote.RetrofitClient
import com.example.themealapp.ui.theme.TheMealAppTheme
import com.example.themealapp.viewmodel.MealViewModel
import com.example.themealapp.viewmodel.MealsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheMealAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MealScreen(
                        viewModel = viewModel(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MealScreen(viewModel: MealViewModel, modifier: Modifier = Modifier){
    //create repo and viewmodel object

    val state by viewModel.mealState.observeAsState(MealsState.Loading)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when(state){
            is MealsState.Error ->{}
            MealsState.Loading -> CircularProgressIndicator()
            is MealsState.Success -> {
                LazyColumn(contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp))
                {
                    items((state as MealsState.Success).meals){ meals ->
                        MealItems(meals)
                    }
                }
            }
        }
    }
}

@Composable
fun MealItems(meal: Meal) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
          AsyncImage(
              model = meal.strMealThumb,
              contentDescription = meal.strMeal,
              modifier = Modifier.size(100.dp)
          )
            Text(
                text = meal.strMeal
            )
        }
    }
}