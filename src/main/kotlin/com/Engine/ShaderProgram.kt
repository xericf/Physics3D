package com.Engine

import com.IO.FileUtil
import org.lwjgl.opengl.GL20.*
import kotlin.properties.Delegates

class ShaderProgram(private val shaderInfos : MutableList<ShaderInfo>) {

    private var programId : Int = 0

    // List of shader IDs
    private val shaders : MutableList<Int> = mutableListOf()
    init {
        programId = glCreateProgram()
        if (programId == 0) {
            throw Exception("Could not create ShaderProgram")
        }

        // load and create each shader
        shaderInfos.forEach {
            val shaderCode = FileUtil.loadShader(it.fileName)
            shaders.add(createShader(shaderCode, it.type))
        }

        link()
    }

    private fun createShader(shaderCode : String, shaderType : Int) : Int {

        val shaderId : Int = glCreateShader(shaderType)
        if (shaderId == 0) {
            throw Exception("Shader not created correctly of type: $shaderType")
        }

        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw Exception("Shader not compiled correctly: ${glGetShaderInfoLog(shaderId, 1024)}")
        }

        glAttachShader(programId, shaderId)
        return shaderId
    }

    private fun link() {

        // links the program by programId, creates executables using currently attached vertex, fragment, and geometry shaders
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024))
        }

        // detach shaders now that program is linked
        shaders.forEach {
            glDetachShader(programId, it)
        }

        shaders.forEach(::glDeleteShader)

        glValidateProgram(programId)
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            throw Exception("WARNING: validating program while linking, saw ${glGetProgramInfoLog(programId, 1024)}")
        }
    }

    fun bind() {
        glUseProgram(programId)
    }

    fun unbind() {
        glUseProgram(0)
    }

    fun cleanup() {
        unbind()
        if (programId != 0) {
            glDeleteProgram(programId)
        }
    }

    data class ShaderInfo(val fileName : String, val type : Int)
}