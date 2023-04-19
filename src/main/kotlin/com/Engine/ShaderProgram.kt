package com.Engine

import org.lwjgl.opengl.GL20.*
import kotlin.properties.Delegates

class ShaderProgram {

    private var programId : Int = 0
    private var vertexShaderId : Int = 0
    private var fragmentShaderId : Int = 0

    init {
        programId = glCreateProgram()
        if (programId == 0) {
            throw Exception("Could not create ShaderProgram")
        }
    }

    fun createVertexShader(shaderCode: String) {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER)
    }

    fun createFragmentShader(shaderCode: String) {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER)
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

    fun link() {

        // links the program by programId, creates executables using currently attached vertex, fragment, and geometry shaders
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024))
        }

        // detach shaders now that program is linked
        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId)
        }

        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId)
        }

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
}