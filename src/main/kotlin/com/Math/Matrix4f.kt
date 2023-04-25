package com.Math

class Matrix4f {

    private val matrix: FloatArray = FloatArray(16) { 0.0f }

    // Creates an identity matrix
    val identity: FloatArray = FloatArray(16) { if (it % 5 == 0) 1.0f else 0.0f }

    fun add(other: Matrix4f): Matrix4f {
        val otherMatrix = other.matrix
        val answer = Matrix4f()
        val answerMatrix = answer.matrix

        for (i in matrix.indices) {
            answerMatrix[i] = matrix[i] + otherMatrix[i]
        }

        return answer
    }

    fun multiply(other: Matrix4f): Matrix4f {
        val otherMatrix = other.matrix
        val answer = Matrix4f()
        val answerMatrix = answer.matrix

        for (i in 0 until 4) { // row
            for (j in 0 until 4) { // col
                for (k in 0 until 4) {
                    answerMatrix[i * 4 + j] += matrix[i * 4 + k] * otherMatrix[k * 4 + j] // dot product
                }
            }
        }

        return answer
    }

    fun get(row : Int, col : Int) : Float {
        return matrix[row * 4 + col]
    }
}