package com.lavinef.motorevents

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.lavinef.motorevents.ui.app.MainAppScreen
import com.lavinef.motorevents.ui.app.theme.MotorEventsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MotorEventsTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainAppScreen()
                }
            }
        }

    }
}
