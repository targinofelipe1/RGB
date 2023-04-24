package com.example.rgb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Formulario : AppCompatActivity() {
    private lateinit var etNome: EditText
    private lateinit var etVermelho: EditText
    private lateinit var etVerde: EditText
    private lateinit var etAzul: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        etNome = findViewById(R.id.etNome)
        etVermelho = findViewById(R.id.etVermelho)
        etVerde = findViewById(R.id.etVerde)
        etAzul = findViewById(R.id.etAzul)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnCancelar = findViewById(R.id.btnCancelar)

        val cor = intent.getSerializableExtra("COR") as? Cor

        cor?.let {
            etNome.setText(it.nome)
            etVermelho.setText(it.vermelho.toString())
            etVerde.setText(it.verde.toString())
            etAzul.setText(it.azul.toString())
        }

        btnSalvar.setOnClickListener { save(cor) }
        btnCancelar.setOnClickListener { cancel() }
    }

    private fun save(cor: Cor?) {
        val name = etNome.text.toString()
        val red = etVermelho.text.toString().toInt()
        val green = etVerde.text.toString().toInt()
        val blue = etAzul.text.toString().toInt()
        val newOrUpdatedCor = cor?.let {
            it.nome = name
            it.vermelho = red
            it.verde = green
            it.azul = blue
            it
        } ?: Cor(name, red, green, blue)

        val intent = Intent().apply {
            putExtra("COR", newOrUpdatedCor)
            putExtra("Nome", name)
            putExtra("Vermelho", red)
            putExtra("Verde", green)
            putExtra("Azul", blue)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun cancel() {
        finish()
    }
}