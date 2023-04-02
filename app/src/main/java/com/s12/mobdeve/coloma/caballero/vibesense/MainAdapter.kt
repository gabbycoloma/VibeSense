package com.s12.mobdeve.coloma.caballero.vibesense

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val Mood:ArrayList<Mood>)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    companion object{
        const val emojiKey : String = "EMOJI_KEY"
        const val nameKey : String = "NAME_KEY"
        const val descriptionKey : String = "DESCRIPTION_KEY"
        const val dateKey : String = "DATE_KEY"
    }


    interface OnAddItemListener {
        fun onAddItem(mood: Mood)
    }
    class ViewHolder(moodView: View) : RecyclerView.ViewHolder(moodView){
        val moodEmoji : ImageView = moodView.findViewById(R.id.mood_emoji)
        val moodName : TextView = moodView.findViewById(R.id.mood_name)
        val moodDesc : TextView = moodView.findViewById(R.id.mood_desc)
        val moodDate: TextView = moodView.findViewById(R.id.mood_date)
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
        holder.moodDate.text = mood.date

        holder.itemView.setOnClickListener{
            val args = Bundle()
            args.putInt(emojiKey, mood.emoji)
            args.putString(nameKey, mood.name)
            args.putString(descriptionKey, mood.desc)
            args.putString(dateKey, mood.date)

            val fragment = ViewMood()
            fragment.arguments = args

            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

}