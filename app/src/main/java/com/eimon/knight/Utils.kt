package com.eimon.knight

internal object Utils {
    internal val emptyList = listOf(Pair(0, 0)) as MutableList<Pair<Int, Int>>

    // Knight moves two squares in one direction (horizontally or vertically)
    // and then // one square perpendicular to that direction.
    private fun validMoves(startX: Int, startY: Int, boardSize: Int): MutableList<Pair<Int, Int>> {
        val moves = listOf(
            Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1),
            Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2)
        )
        val validMoves = mutableListOf<Pair<Int, Int>>()

        for ((dx, dy) in moves) {
            val newX = startX + dx
            val newY = startY + dy

            if (newX in 1..boardSize && newY in 1..boardSize) {
                validMoves.add(Pair(newX, newY))
            }
        }

        return validMoves
    }

    private fun getKnightMoves(
        startingPoint: IntArray,
        boardSize: Int
    ): MutableList<Pair<Int, Int>> {
        val (row, column) = startingPoint
        if (row == 0 && column == 0) return emptyList
        return validMoves(row, column, boardSize)
    }

    internal fun parseToIntArray(str: String): IntArray {
        val parts = str.split(",").map { it.trim().toInt() }
        if (parts.size == 2) return intArrayOf(parts[0], parts[1])
        return intArrayOf(0, 0)
    }

    /**
     * Breadth-First Search
     * @return MutableList of positions representing the shortest path, or (0,0) if no path found
     */
    internal fun findKnightPathBFS(
        startingPoint: IntArray,
        endingPoint: IntArray,
        boardSize: Int,
    ): MutableList<Pair<Int, Int>> {

        val startX = startingPoint[0]
        val startY = startingPoint[1]
        val endX = endingPoint[0]
        val endY = endingPoint[1]

        // Validate inputs
        if (startX !in 1..boardSize || startY !in 1..boardSize ||
            endX !in 1..boardSize || endY !in 1..boardSize
        ) {
            return emptyList
        }

        // Check if we're already at the destination
        if (startX == endX && startY == endY) {
            return mutableListOf(Pair(startX, startY))
        }

        // Queue stores: (current position, path to get there)
        val queue = ArrayDeque<Pair<Pair<Int, Int>, MutableList<Pair<Int, Int>>>>()
        val visited = mutableSetOf<Pair<Int, Int>>()

        // Start with the initial position
        val startPos = Pair(startX, startY)
        queue.add(Pair(startPos, mutableListOf(startPos)))
        visited.add(startPos)

        var depth = 0

        while (queue.isNotEmpty() && depth < 256) {
            val (currentPos, path) = queue.removeFirst()
            val (currentX, currentY) = currentPos

            // Check if we reached the destination
            if (currentX == endX && currentY == endY) {
                return path
            }

            // Get all possible moves from current position using your existing function
            val moves = getKnightMoves(intArrayOf(currentX, currentY), boardSize)

            for (move in moves) {
                if (!visited.contains(move)) {
                    visited.add(move)
                    val newPath = path.toMutableList() // Create a mutable copy
                    newPath.add(move)
                    queue.add(Pair(move, newPath))
                }
            }

            depth++
        }

        return emptyList // No path found within max depth
    }
}