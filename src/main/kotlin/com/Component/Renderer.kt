package com.Component

import com.Engine.GameObject
import com.Engine.Loader
import com.Engine.ShaderProgram
import com.IO.FileUtil
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER
import kotlin.properties.Delegates

class Renderer(gameObject: GameObject) : Component(gameObject) {

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

        shaderProgram = ShaderProgram(mutableListOf(
            ShaderProgram.ShaderInfo("/vertex.vert", GL_VERTEX_SHADER),
            ShaderProgram.ShaderInfo("/fragment.frag", GL_FRAGMENT_SHADER)
        ))


    }

    fun render() {
        shaderProgram.bind()

        Loader.bindVao(vaoId)

        glDrawArrays(GL_TRIANGLES, 0, 3)
        Loader.unbindVao()

        shaderProgram.unbind()
    }

    fun cleanup() {
        shaderProgram.cleanup()
        // VBO & VAO cleaned in Loader
    }

}