package com.example.rgb

class Cores {
    private var lista = mutableListOf<Cor>()

    fun add(cor: Cor) {
        lista.add(cor)
    }

    fun get(index: Int): Cor {
        return lista[index]
    }

    fun get(): MutableList<Cor> {
        return lista
    }

    fun delete(index: Int) {
        lista.removeAt(index)
    }

    fun size(): Int {
        return lista.size
    }
}