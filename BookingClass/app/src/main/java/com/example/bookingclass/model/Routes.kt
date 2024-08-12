package com.example.bookingclass.model

sealed class Routes(val route:String) {
    object PreLoginView:Routes("PreLoginView")
    object LoginView:Routes("LoginView")
    object MainView:Routes("MainView")
    object RegisterView:Routes("RegisterView")
}