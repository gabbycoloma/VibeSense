package com.s12.mobdeve.coloma.caballero.vibesense.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.s12.mobdeve.coloma.caballero.vibesense.Model.Mood
import com.s12.mobdeve.coloma.caballero.vibesense.R
import com.s12.mobdeve.coloma.caballero.vibesense.ViewMood

class MainAdapter
    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val moodList = ArrayList<Mood>()
    companion object{
        const val emojiKey : String = "EMOJI_KEY"
        const val nameKey : String = "NAME_KEY"
        const val descriptionKey : String = "DESCRIPTION_KEY"
        const val dateKey : String = "DATE_KEY"
    }


    interface OnAddItemListener {
        fun onAddItem(mood: Mood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mood_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moodList.size
    }

    fun updateMoodList(moodList: List<Mood>){
        this.moodList.clear()
        this.moodList.addAll(moodList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mood = moodList[position]
        holder.moodName.text = mood.name
        holder.moodDesc.text = mood.description
        holder.moodDate.text = mood.date
        when {
            mood.name.equals("happy", true) -> {
                holder.moodEmoji.setImageResource(R.drawable.mood1)
            }
            mood.name.equals("good", true) -> {
                holder.moodEmoji.setImageResource(R.drawable.mood2)
            }
            mood.name.equals("neutral", true) -> {
                holder.moodEmoji.setImageResource(R.drawable.mood3)
            }
            mood.name.equals("sad", true) -> {
                holder.moodEmoji.setImageResource(R.drawable.mood4)
            }
            mood.name.equals("angry", true) -> {
                holder.moodEmoji.setImageResource(R.drawable.mood5)
            }
        }

        holder.itemView.setOnClickListener{
            val args = Bundle()

            args.putString(nameKey, mood.name)
            args.putString(descriptionKey, mood.description)
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

    class ViewHolder(moodView: View) : RecyclerView.ViewHolder(moodView){
        val moodEmoji : ImageView = moodView.findViewById(R.id.mood_emoji)
        val moodName : TextView = moodView.findViewById(R.id.mood_name)
        val moodDesc : TextView = moodView.findViewById(R.id.mood_desc)
        val moodDate: TextView = moodView.findViewById(R.id.mood_date)
    }


}