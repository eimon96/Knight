package com.eimon.knight

internal object Utils {

    // Knight moves two squares in one direction (horizontally or vertically)
    // and then // one square perpendicular to that direction.
    internal fun validMoves(startX: Int, startY: Int, boardSize: Int): MutableList<Pair<Int, Int>> {
        val moves = listOf(
            Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1),
            Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2)
        )
        val validMoves = mutableListOf<Pair<Int, Int>>()

        for ((dx, dy) in moves) {
            val newX = startX + dx
            val newY = startY + dy

            if (newX in 1 .. boardSize && newY in 1 .. boardSize) {
                validMoves.add(Pair(newX, newY))
            }
        }

        return validMoves
    }
}