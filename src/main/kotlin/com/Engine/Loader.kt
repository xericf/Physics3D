package com.Engine

import org.lwjgl.opengl.GL15.glDeleteBuffers
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer

object Loader {

    // VAO: The purpose of a Vertex Array Object is to store configurations for each unique
    // mesh with vertex attribute pointers, this will let openGL know how to interpret a mesh's vertex data
    // VBO: A VBO stores the actual vertex data, such as positions, colors, normals, and textures.
    // Create one VBO and VAO for each unique mesh.
    private val vaoList = mutableListOf<Int>()
    private val vboList = mutableListOf<Int>()

    // Creates VAO and binds it to current context
    fun createVaoAndBind() : Int {
        val vaoId = glGenVertexArrays()
        glBindVertexArray(vaoId)
        vaoList.add(vaoId)
        return vaoId;
    }

    fun createVbo(data : FloatArray, usage: Int = GL_STATIC_DRAW) : Int {
        // Create and bind VBO object
        val vboId = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vboId)

        // allocate memory and set the data for buffer object
        val buffer = createFloatBuffer(data)
        glBufferData(GL_ARRAY_BUFFER, buffer, usage)
        // free the CPU-side memory and unbind the buffer
        MemoryUtil.memFree(buffer)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        vboList.add(vboId);

        return vboId
    }

    fun configureAttribPointer(vboId : Int, index: Int, size : Int = 3, type : Int = GL_FLOAT, stride : Int = 0, pointer : Long = 0) {
        glBindBuffer(GL_ARRAY_BUFFER, vboId)
        glEnableVertexAttribArray(index) // Enable vertex attrib array (allows for data to be sent to GPU)
        glVertexAttribPointer(index, size, type, false, stride, pointer)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun bindVao(vaoId : Int) {
        glBindVertexArray(vaoId)
    }

    fun unbindVao() {
        glBindVertexArray(0)
    }

    private fun createFloatBuffer(data : FloatArray) : FloatBuffer {
        val buffer : FloatBuffer = MemoryUtil.memAllocFloat(data.size)
        buffer.put(data).flip()
        return buffer
    }

    fun cleanup() {
        vaoList.forEach(::glDeleteVertexArrays)
        vboList.forEach(::glDeleteBuffers)
    }

}