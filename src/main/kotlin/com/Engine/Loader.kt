package com.Engine

import org.lwjgl.opengl.GL15.glDeleteBuffers
import org.lwjgl.opengl.GL30.glDeleteVertexArrays

class Loader {

    // VAO: The purpose of a Vertex Array Object is to store configurations for each unique
    // mesh with vertex attribute pointers, this will let openGL know how to interpret a mesh's vertex data
    // VBO: A VBO stores the actual vertex data, such as positions, colors, normals, and textures.
    // Create one VBO and VAO for each unique mesh.
    private val vaoList = mutableListOf<Int>()
    private val vboList = mutableListOf<Int>()

    fun createVao() {

    }

    fun cleanup() {
        vaoList.forEach(::glDeleteVertexArrays)
        vboList.forEach(::glDeleteBuffers)
    }

}