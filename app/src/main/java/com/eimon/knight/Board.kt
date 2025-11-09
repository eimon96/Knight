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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun Board(size: Int) {
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
                            .clickable { /* Handle click */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.knight_piece),
                            fontSize = 20.sp,
                            color = colorResource(R.color.yellow)
                        )
                    }
                }

            }
        }
    }
}