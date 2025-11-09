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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

class Board(private val size: Int) {
    // Coordinates (row, column)
    private var startingPoint by mutableStateOf(intArrayOf(0, 0))
    private var endingPoint by mutableStateOf(intArrayOf(0, 0))

    private fun setStartingPoint(row: Int, column: Int) {
        startingPoint = intArrayOf(row, column)
    }

    private fun setEndingPoint(row: Int, column: Int) {
        endingPoint = intArrayOf(row, column)
    }

    private fun setSelected(row: Int, column: Int) {
        if (isStartingPoint(0, 0)) {
            setStartingPoint(row, column)
        } else if (isEndingPoint(0, 0) && !isStartingPoint(row, column)) {
            setEndingPoint(row, column)
        }
    }

    private fun isStartingPoint(row: Int, column: Int): Boolean {
        return (startingPoint.contentEquals(intArrayOf(row, column)))
    }

    private fun isEndingPoint(row: Int, column: Int): Boolean {
        return (endingPoint.contentEquals(intArrayOf(row, column)))
    }

    fun reset() {
        setStartingPoint(0, 0)
        setEndingPoint(0, 0)
    }

    @Composable
    fun Draw() {
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
                                .size(40.dp)
                                .background(colorResource(if ((row + col) % 2 == 0) R.color.blue else R.color.red))
                                .clickable { setSelected(row, col) },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isStartingPoint(row, col)) {
                                Text(
                                    stringResource(R.string.knight_piece),
                                    fontSize = 30.sp,
                                    color = colorResource(R.color.yellow)
                                )
                            }
                            if (isEndingPoint(row, col)) {
                                Text(
                                    stringResource(R.string.flag),
                                    fontSize = 30.sp,
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}