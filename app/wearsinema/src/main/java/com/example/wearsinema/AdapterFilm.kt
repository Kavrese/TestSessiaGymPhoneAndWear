package com.example.wearsinema

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wearsinema.Internet.Models.ModelFilm
import com.squareup.picasso.Picasso

class AdapterFilm(private val filmsList: MutableList<ModelFilm>) :

    RecyclerView.Adapter<AdapterFilm.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.img)
        val name_film = itemView.findViewById<TextView>(R.id.name_film)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false))
    }

    override fun getItemCount(): Int = filmsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load("http://cinema.areas.su/up/images/" + filmsList[position].poster)
            .into(holder.img)
        holder.name_film.text = filmsList[position].name
    }

}
