package com.State

// StateManagers are used for

class StateManager {
    private val states: MutableList<State> = mutableListOf()

    fun pushState(state: State) {
        states.add(state)
        state.init()
    }

    fun popState() {
        states.lastOrNull()?.dispose()
        states.removeLastOrNull()
    }

    fun replaceState(state: State) {
        popState()
        pushState(state)
    }

    fun currentState(): State? = states.lastOrNull()

    fun update(dt: Float) {
        currentState()?.update(dt)
    }

    fun render() {
        currentState()?.render()
    }

    fun handleInput() {
        currentState()?.handleInput()
    }

    fun dispose() {
        states.forEach { it.dispose() }
        states.clear()
    }
}
