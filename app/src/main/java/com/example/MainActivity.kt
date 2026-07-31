package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.navigation.AcademyAppMain
import com.example.ui.theme.CyberHackAcademyTheme
import com.example.viewmodel.AcademyViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: AcademyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CyberHackAcademyTheme {
                AcademyAppMain(viewModel = viewModel)
            }
        }
    }
}
