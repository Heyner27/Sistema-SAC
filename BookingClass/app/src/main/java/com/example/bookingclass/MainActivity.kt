package com.example.bookingclass

import com.example.bookingclass.ui.theme.BookingClassTheme
import com.example.bookingclass.ui.viewmodels.LoginViewModel
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookingclass.model.Routes
import com.example.bookingclass.ui.screens.MainScreen
import com.example.bookingclass.ui.screens.LoginScreen
import com.example.bookingclass.ui.screens.RegisterScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookingclass.ui.screens.PreLoginScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookingClassTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                val loginViewModel: LoginViewModel = viewModel()
                val navigationController  = rememberNavController()

                NavHost(navController = navigationController, startDestination = Routes.PreLoginView.route) {

                    composable("PreLoginView") { PreLoginScreen(navigationController) }
                    composable("LoginView") {    LoginScreen(navigationController, loginViewModel) }
                    composable("RegisterView") {
                        RegisterScreen(onRegister = { success ->
                            if (success) {
                                navigationController.navigate("LoginView") {
                                    popUpTo("RegisterView") { inclusive = true }
                                }
                            }
                        })
                    }
                    //"HomeView/{username}",
                    composable("MainView") { MainScreen(navigationController,"karen") }
                    composable("HomeView")
                    // arguments = listOf(navArgument("username") { type = NavType.StringType}
                    { backStackEntry ->
                        // val userName = backStackEntry.arguments?.getString("username")
                        //MainScreen(navigationController, userName)
                    }
                }
            }
        }


    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookingClassTheme {
        Greeting("Android")
    }
}