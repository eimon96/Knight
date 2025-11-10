package com.eimon.knight

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.eimon.knight.Utils.validMoves
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class Board(private val activity: MainActivity) {
    // Coordinates (row, column)
    private var startingPoint by mutableStateOf(intArrayOf(0, 0))
    private var endingPoint by mutableStateOf(intArrayOf(0, 0))

    private var knightMoves = mutableListOf<Pair<Int, Int>>()

    private fun setStartingPoint(row: Int, column: Int) {
        startingPoint = intArrayOf(row, column)
    }

    private fun setEndingPoint(row: Int, column: Int) {
        endingPoint = intArrayOf(row, column)
    }

    private fun setKnightMoves(moves: MutableList<Pair<Int, Int>>) {
        knightMoves = moves
    }

    private fun setSelected(row: Int, column: Int, boardSize: Int) {
        if (isStartingPoint(0, 0)) {
            setStartingPoint(row, column)
        } else if (isEndingPoint(0, 0) && !isStartingPoint(row, column)) {
            setEndingPoint(row, column)

            getKnightMovesAsync(activity.lifecycleScope, boardSize) { moves ->
                setKnightMoves(moves)
            }
        }
    }

    private fun isStartingPoint(row: Int, column: Int): Boolean {
        return (startingPoint.contentEquals(intArrayOf(row, column)))
    }

    private fun isEndingPoint(row: Int, column: Int): Boolean {
        return (endingPoint.contentEquals(intArrayOf(row, column)))
    }

    private fun getKnightMoves(boardSize: Int): MutableList<Pair<Int, Int>> {
        val defaultValue = listOf(Pair(0, 0)) as MutableList<Pair<Int, Int>>
        if (isStartingPoint(0, 0)) return defaultValue
        val (row, column) = startingPoint
        return validMoves(row, column, boardSize)
    }

    internal fun getKnightMovesAsync(
        scope: CoroutineScope,
        boardSize: Int,
        callback: (MutableList<Pair<Int, Int>>) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            val moves = getKnightMoves(boardSize)
            withContext(Dispatchers.Main) {
                callback(moves)
            }
        }
    }

    internal fun reset() {
        val defaultValue = listOf(Pair(0, 0)) as MutableList<Pair<Int, Int>>
        setStartingPoint(0, 0)
        setEndingPoint(0, 0)
        setKnightMoves(defaultValue)
    }

    @Composable
    internal fun Draw(size: Int) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        val squareSize = (screenWidth - 60) / size
        val itemSize = squareSize - 10

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            for (row in 1..size) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (col in 1..size) {
                        Box(
                            modifier = Modifier
                                .size(squareSize.dp)
                                .background(colorResource(if ((row + col) % 2 == 0) R.color.blue else R.color.red))
                                .clickable { setSelected(row, col, size) },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isStartingPoint(row, col)) {
                                Text(
                                    stringResource(R.string.knight_piece),
                                    fontSize = itemSize.sp,
                                    color = colorResource(R.color.yellow)
                                )
                            }
                            if (isEndingPoint(row, col)) {
                                Text(
                                    stringResource(R.string.flag),
                                    fontSize = itemSize.sp,
                                )
                            }
                            if (Pair(row, col) in knightMoves) {
                                Text(
                                    stringResource(R.string.horse_shoe),
                                    fontSize = itemSize.sp,
                                    color = colorResource(R.color.yellow)
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}