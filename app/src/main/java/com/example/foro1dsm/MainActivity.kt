package com.example.foro1dsm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.foro1dsm.navigation.AppNavigation
import com.example.foro1dsm.ui.theme.Foro1DSMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Foro1DSMTheme {
                AppNavigation()
            }
        }
    }
}