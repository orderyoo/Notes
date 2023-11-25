package com.example.anew

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anew.ui.theme.NEWTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dependencies.init(applicationContext)
        val viewModel = MainViewModel(Dependencies.repository)
        viewModel.getListFromDB()

        setContent {
            if(isTablet()){
                val intent = Intent(this, TabletActivity::class.java)
                startActivity(intent)
            }

            val navController = rememberNavController()

            var list by remember { mutableStateOf(viewModel.data.value) }
            viewModel.data.observe(this){
                list = it
            }
            NEWTheme {
                NavHost(
                    navController = navController,
                    startDestination = "mainScreen"
                ) {
                    composable("mainScreen") {
                        viewModel.getListFromDB()
                        MainPhoneScreen(navController, viewModel, list)
                    }
                    composable("OpenScreen") {
                        OpenScreen(navController, viewModel)
                    }
                }
            }

        }
    }
}



