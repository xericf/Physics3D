package com.Component

import com.Engine.AttribConstant
import com.Engine.GameObject
import com.Engine.Loader


class Mesh(positions : FloatArray, private val numVerticies : Int, gameObject: GameObject) : Component(gameObject) {

    var vaoId : Int
        private set
    private var vboIds : MutableList<Int> = mutableListOf()

    init {
        vaoId = Loader.createVaoAndBind()
        val vboPosition = Loader.createVbo(positions)
        vboIds.add(vboPosition)
        Loader.configureAttribPointer(vboPosition, AttribConstant.POSITION)
        Loader.unbindVao()
    }


}

