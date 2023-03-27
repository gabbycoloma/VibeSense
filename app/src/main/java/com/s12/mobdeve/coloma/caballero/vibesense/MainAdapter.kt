package com.s12.mobdeve.coloma.caballero.vibesense

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val Mood:ArrayList<Mood>)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(moodView: View) : RecyclerView.ViewHolder(moodView){
        val moodEmoji : ImageView = moodView.findViewById(R.id.mood_emoji)
        val moodName : TextView = moodView.findViewById(R.id.mood_name)
        val moodDesc : TextView = moodView.findViewById(R.id.mood_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mood_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Mood.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mood = Mood[position]
        holder.moodEmoji.setImageResource(mood.emoji)
        holder.moodName.text = mood.name
        holder.moodDesc.text = mood.desc
    }
}