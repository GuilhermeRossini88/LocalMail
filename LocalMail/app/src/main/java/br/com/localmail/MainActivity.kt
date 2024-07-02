package br.com.localmail


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.localmail.screens.Email
import br.com.localmail.screens.Login
import br.com.localmail.screens.SignUp
import br.com.localmail.screens.SuperCalendar
import br.com.localmail.ui.Inbox

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login"){
                    Login(navController)
                }
                composable("signup"){
                    SignUp(navController)
                }
                composable("inbox"){
                    Inbox(navController)
                }
                composable("calendar"){
                    SuperCalendar(navController)
                }
                composable("email"){
                    Email(navController)
                }
            }

        }
    }
}
