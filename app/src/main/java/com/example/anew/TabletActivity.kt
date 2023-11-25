package com.example.anew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.anew.ui.theme.NEWTheme

class TabletActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel(Dependencies.repository)
        viewModel.getListFromDB()

        setContent {
            if(!isTablet()){
                finish()
            }
            NEWTheme {
                TabletScreen(viewModel, this)
            }
        }
    }
}

