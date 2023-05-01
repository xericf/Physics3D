package com.Engine

import com.Component.Component
import com.Component.Script

class GameObject {

    private val scripts : MutableList<Script> = ArrayList()

    fun start() {

        for (script in scripts) {
            script.start()
        }

    }
    fun update() {
        for (script in scripts) {
            script.update()
        }
    }
}