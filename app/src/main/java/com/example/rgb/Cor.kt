package com.example.rgb

import java.io.Serializable


class Cor(var nome: String, var vermelho: Int, var verde: Int, var azul: Int): Serializable {
    companion object {
        private var id = 0
    }

    val id: Int = Companion.id++

    override fun toString(): String {
        return "$nome - R: $vermelho G: $verde B: $azul"
    }
}