package com.Engine

import com.IO.FileUtil
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer

class Renderer {

    init {
        val vertices = floatArrayOf(
            0.0f, 0.5f, 0.0f,
            -0.5f, 0.0f, 0.0f,
            0.5f, 0.0f, 0.0f
            )

        val loader = Loader()

        loader.createVaoAndBind()
        loader.createVbo(vertices)


        val frag = FileUtil.loadShader("/fragment.frag")
        val vert = FileUtil.loadShader("/vertex.vert")

        val shaderProgram = ShaderProgram()
        shaderProgram.createVertexShader(vert)
        shaderProgram.createFragmentShader(frag)
        shaderProgram.link()

        loader.cleanup()
    }

}