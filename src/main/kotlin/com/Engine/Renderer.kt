package com.Engine

import com.IO.FileUtil
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer
import kotlin.properties.Delegates

class Renderer {

    private var shaderProgram : ShaderProgram
    private var vaoId by Delegates.notNull<Int>()
    private var vboId by Delegates.notNull<Int>()

    init {
        val vertices = floatArrayOf(
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
            )

        vaoId = Loader.createVaoAndBind()
        vboId = Loader.createVbo(vertices)
        Loader.configureAttribPointer(vboId = vboId, index = 0)

        val frag = FileUtil.loadShader("/fragment.frag")
        val vert = FileUtil.loadShader("/vertex.vert")

        shaderProgram = ShaderProgram()
        shaderProgram.createVertexShader(vert)
        shaderProgram.createFragmentShader(frag)
        shaderProgram.link()

    }

    fun render() {
        shaderProgram.bind()

        Loader.bindVao(vaoId)

        glDrawArrays(GL_TRIANGLES, 0, 3)
        Loader.unbindVao()

        shaderProgram.unbind()
    }


}