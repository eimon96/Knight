package com.eimon.knight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

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
    val board = Board(boardSize)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.green))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            board.Draw()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
        ) {
            ResetButton { board.reset() }
        }
    }
}
