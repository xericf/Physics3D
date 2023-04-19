package com.IO

import java.io.File

class FileUtil {

    // STATICS
    companion object {
        fun loadShader(fileName : String) : String {
            return File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
        }
    }

}