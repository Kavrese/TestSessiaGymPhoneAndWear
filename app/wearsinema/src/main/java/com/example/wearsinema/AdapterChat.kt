package com.example.wearsinema

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wearsinema.Internet.ModelsChat.Chatlists
import java.util.*

class AdapterChat(val list_chat: List<Chatlists>): RecyclerView.Adapter<AdapterChat.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val num = itemView.findViewById<TextView>(R.id.num)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_rec, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list_chat.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list_chat[position].name
        holder.num.text = Random().nextInt(10).toString()
    }
}