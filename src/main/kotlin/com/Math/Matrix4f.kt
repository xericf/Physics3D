package com.Math

class Matrix4f {

    private var matrix : Array<Array<Float>> = Array(4) { Array(4) {0.0f}}

    // Creates an identity matrix
    val identity : Array<Array<Float>> = Array(4) {i -> Array(4) {
        j -> if (i == j) {
            1.0f
        } else {
            0.0f
    } } }

    fun add(other : Matrix4f) : Matrix4f {
        val otherMatrix = other.matrix
        val answer = Matrix4f()
        val answerMatrix = answer.matrix

        for (i in otherMatrix.indices) {
            for (j in otherMatrix[i].indices) {
                answerMatrix[i][j] = otherMatrix[i][j] + matrix[i][j]
            }
        }
        return answer
    }

    fun multiply(other : Matrix4f) : Matrix4f {
        val otherMatrix = other.matrix
        val answer = Matrix4f()
        val answerMatrix = answer.matrix

        for (i in 0 until 4) { // row
            for (j in 0 until 4) { // col
                for (k in 0 until 4) {
                    answerMatrix[i][j] += matrix[i][k] * otherMatrix[k][j] // dot product
                }
            }
        }

        return answer
    }



}