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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eimon.knight.Utils.parseToIntArray

class MainActivity : ComponentActivity() {
    private val board = Board(this)
    private val storage = StorageHandler(this)
    private var boardSize by mutableIntStateOf(8)
    private var maxMoves by mutableIntStateOf(3)
    private var savedStartingPoint by mutableStateOf(intArrayOf(0, 0))
    private var savedEndingPoint by mutableStateOf(intArrayOf(0, 0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadStorage()
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

    private fun onIncreaseMaxMoves() {
        board.reset()
        maxMoves += 1
    }

    private fun onDecreaseMaxMoves() {
        if (maxMoves == 1) return
        board.reset()
        maxMoves -= 1
    }

    private fun loadStorage() {
        boardSize = storage.getFromPrefs("boardSize", "8").toInt()
        maxMoves = storage.getFromPrefs("maxMoves", "3").toInt()

        savedStartingPoint = parseToIntArray(
            storage.getFromPrefs(
                "startingPoint", "0,0"
            )
        )
        board.setSelected(savedStartingPoint[0], savedStartingPoint[1], boardSize, maxMoves)
        savedEndingPoint = parseToIntArray(
            storage.getFromPrefs(
                "endingPoint", "0,0"
            )
        )
        board.setSelected(savedEndingPoint[0], savedEndingPoint[1], boardSize, maxMoves)
    }

    override fun onStop() {
        super.onStop()
        storage.saveToPrefs("boardSize", boardSize.toString())
        storage.saveToPrefs("maxMoves", maxMoves.toString())
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

                board.Draw(boardSize, maxMoves)

                Box(modifier = Modifier.align(Alignment.End)) {
                    TextButton(
                        stringResource(R.string.max_moves),
                        maxMoves,
                        { onIncreaseMaxMoves() },
                        { onDecreaseMaxMoves() })
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