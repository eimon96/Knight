package com.eimon.knight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private var boardSize by mutableIntStateOf(8)
    private var maxMoves by mutableIntStateOf(3)
    private val board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawContent()
        }
    }

    private fun onIncreaseBoardSize() {
        if (boardSize == 16) return
        board.reset()
        boardSize += 1
    }

    private fun onDecreaseBoardSize() {
        if (boardSize == 6) return
        board.reset()
        boardSize -= 1
    }

    @Composable
    private fun DrawContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.green))
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                TextButton(
                    stringResource(R.string.board_size), boardSize,
                    { onIncreaseBoardSize() }, { onDecreaseBoardSize() })

                board.Draw(boardSize)

                Box(modifier = Modifier.align(Alignment.End)) {
                    TextButton(stringResource(R.string.max_moves), maxMoves, {}, {})
                }
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
}