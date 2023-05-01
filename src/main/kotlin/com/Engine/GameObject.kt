package com.Engine

import com.Component.Component
import com.Component.Script
import com.Exception.ComponentAlreadyExistsException


class GameObject {

    private val scripts : MutableList<Script> = ArrayList()
    val components = mutableMapOf<String, Component>()
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

    fun <T : Component> addComponent(component: T) {
        val name = component::class.simpleName!!
        if (components[name] == null) {
            components[name] = component
        } else {
            throw ComponentAlreadyExistsException("Component of type $name already exists")
        }
    }

    inline fun <reified T : Component> getComponent() : T? {
        return components[T::class.simpleName!!] as? T
    }
}