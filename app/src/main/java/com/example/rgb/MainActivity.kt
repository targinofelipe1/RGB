package com.example.rgb

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var rvCor: RecyclerView
    private lateinit var fabAdicionar: FloatingActionButton
    private var cores = Cores()
    private var Resultado: ActivityResultLauncher<Intent>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.rvCor = findViewById(R.id.rvCor)
        this.fabAdicionar = findViewById(R.id.fabAdicionar)

        this.rvCor.adapter = CorAdapter(this.cores.get())
        (this.rvCor.adapter as CorAdapter).onItemClickListener = OnItemClick()
        ItemTouchHelper(OnSwipe()).attachToRecyclerView(this.rvCor)



        this.Resultado = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val cor = result.data?.getSerializableExtra("COR") as? Cor

                cor?.let {
                    if (cor.id == this.cores.size()) {
                        (this.rvCor.adapter as CorAdapter).add(cor)
                    } else {
                        val changeCor = this.cores.get(cor.id)
                        changeCor.nome = cor.nome
                        changeCor.vermelho = cor.vermelho
                        changeCor.verde = cor.verde
                        changeCor.azul = cor.azul
                        (this.rvCor.adapter as CorAdapter).notifyItemChanged(cor.id)
                    }
                }
            }
        }

        this.fabAdicionar.setOnClickListener {
            val intent = Intent(this, Formulario::class.java)
            this.Resultado?.launch(intent)
        }
    }

    inner class OnItemClick: OnItemCLickRecyclerView {
        override fun onItemClick(position: Int) {
            val intent = Intent(this@MainActivity, Formulario::class.java).apply {
                putExtra("COR", this@MainActivity.cores.get(position))
            }
            this@MainActivity.Resultado?.launch(intent)
        }
    }

    inner class OnSwipe : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
        ItemTouchHelper.START or ItemTouchHelper.END
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            (this@MainActivity.rvCor.adapter as CorAdapter).mover(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition

            if (direction == ItemTouchHelper.END) {
                val builder = AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("Confirmação")
                    setMessage("Apagar cor?")
                    setPositiveButton("Sim") { _, _ ->
                        (this@MainActivity.rvCor.adapter as CorAdapter).deletar(position)
                    }
                    setNegativeButton("Cancelar") { dialog, _ ->
                        dialog.dismiss()
                        (this@MainActivity.rvCor.adapter as CorAdapter).notifyItemChanged(position)
                    }
                }
                builder.create().show()
            } else if (direction == ItemTouchHelper.START) {
                (this@MainActivity.rvCor.adapter as CorAdapter).notifyItemChanged(position)
                val cor = (this@MainActivity.rvCor.adapter as CorAdapter).getCor(position)
                val hexCor = String.format("#%02X%02X%02X", cor.vermelho, cor.verde, cor.verde)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, hexCor)
                }

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
        }
    }
}
