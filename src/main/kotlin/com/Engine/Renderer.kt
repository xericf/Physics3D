package com.Engine

import com.IO.FileUtil

class Renderer {

    init {
        val s = FileUtil.loadShader("/fragment.frag")
        println(s)
    }

}