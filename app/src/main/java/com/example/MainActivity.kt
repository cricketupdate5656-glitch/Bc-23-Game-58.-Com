package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.bc23.BC23Application
import com.example.bc23.core.ui.theme.BC23Theme
import com.example.bc23.navigation.BC23NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as BC23Application).appContainer

        setContent {
            BC23Theme {
                BC23NavGraph(
                    container = appContainer,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
