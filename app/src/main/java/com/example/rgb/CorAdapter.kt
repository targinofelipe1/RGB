package com.example.rgb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CorAdapter(val list: MutableList<Cor>) : RecyclerView.Adapter<CorAdapter.MyHolder>() {

     var onItemClickListener: OnItemCLickRecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list, parent, false

        )
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val cor = list[position]
        holder.tvCorList.text = cor.toString()
    }

    override fun getItemCount(): Int = list.size

    fun deletar(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

    fun mover(from: Int, to: Int) {
        Collections.swap(list, from, to)
        notifyItemMoved(from, to)
    }

    fun getCor(position: Int): Cor {
        return list[position]
    }

    fun add(cor: Cor) {
        this.list.add(cor)
        this.notifyDataSetChanged()
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCorList: TextView

        init {
            this.tvCorList = itemView.findViewById(R.id.tvCorList)
            itemView.setOnClickListener {
                this@CorAdapter.onItemClickListener?.onItemClick(adapterPosition)
            }
        }

    }
}