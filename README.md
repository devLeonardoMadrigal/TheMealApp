# Implement Koin
1. Add dependency to app/build.gradle.kts
```
implementation("io.insert-koin:koin-androidx-compose:4.0.0")
```
2. Create a file in the root folder of the project MealApp.kt. File is the "manual" to actually perform Dependency Injection with Koin
```
private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

val appModule = module {

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

    //Repository
    single {
        MealRepository(get())
    }

    //viewModel
    viewModel{
        MealViewModel(get())
    }
}

```

3. Create file in the root folder MealApp.kt to actually start Koin

```
class MealApp : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MealApp)
            modules(appModule) // Load modules from AppModule app just created
        }
    }
}
```

4. Name the app according to the previous file in AndroidManifest.xml
```
<application
        android:name=".MealApp"
        ...
```

5. Replace the old viewModel with the new one we created MainActivity.kt
```
    viewModel = viewModel() //OLD
    change it to
    viewModel = koinViewModel() //NEW


    //OLD
    fun MealScreen(viewModel: MealViewModel, modifier: Modifier = Modifier)
    //NEW
    fun MealScreen(viewModel: MealViewModel = koinViewModel(), modifier: Modifier = Modifier)
```
