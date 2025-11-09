package com.eimon.knight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawContent()
        }
    }
}

@Composable
fun DrawContent() {
    var boardSize = 8
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(R.color.green)
    ) {
        Board(boardSize)
    }
}