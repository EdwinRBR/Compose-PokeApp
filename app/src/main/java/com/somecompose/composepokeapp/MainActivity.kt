package com.somecompose.composepokeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.somecompose.composepokeapp.ui.navigation.PokeNavigation
import com.somecompose.composepokeapp.ui.theme.ComposePokeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePokeAppTheme {
                PokeNavigation()
            }
        }
    }
}