package com.Util

import com.Component.Mesh

object MeshManager {

    val meshMap : Map<String, Mesh> = mapOf()

    const val TRIANGLE = "TRIANGLE"

    init {
        val vertices = floatArrayOf(
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
        )
    }

}
