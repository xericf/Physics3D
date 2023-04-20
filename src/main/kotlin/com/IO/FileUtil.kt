package com.IO

import java.io.File

class FileUtil {

    // STATICS
    companion object {
        fun loadShader(fileName : String) : String {
            val strs : List<String> = this::class.java.getResourceAsStream(fileName).bufferedReader().readLines()

            return strs.joinToString("\n")
        }
    }

}