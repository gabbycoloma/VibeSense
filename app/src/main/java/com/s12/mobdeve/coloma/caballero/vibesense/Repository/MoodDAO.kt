package com.s12.mobdeve.coloma.caballero.vibesense

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.s12.mobdeve.coloma.caballero.vibesense.Model.Mood
import com.s12.mobdeve.coloma.caballero.vibesense.Model.User


interface MoodDAO {
    fun addMood(mood: Mood)
    fun getMood() : ArrayList<Mood>
    fun updateMood(mood: Mood)
    fun countMoodsByName(name: String, userID: String, callback: (Int) -> Unit)
}
class MoodDAOFireBaseImplementation(var context: Context): MoodDAO {

    private val ROOT = "mood"
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://vibesense-9523f-default-rtdb.asia-southeast1.firebasedatabase.app")
    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser: User? = null



    override fun addMood(mood: Mood) {
        databaseReference.child("Moods").child(mood.userID.toString()).child("emoji").setValue(mood.emoji)
        databaseReference.child("Moods").child(mood.userID.toString()).child("name").setValue(mood.name)
        databaseReference.child("Moods").child(mood.userID.toString()).child("description").setValue(mood.description)
        databaseReference.child("Moods").child(mood.userID.toString()).child("date").setValue(mood.date)
        databaseReference.child("Moods").child(mood.userID.toString()).child("userID").setValue(mood.userID)
    }

    override fun getMood(): ArrayList<Mood> {
        TODO("Not yet implemented")
    }

    override fun updateMood(mood: Mood) {
        TODO("Not yet implemented")
    }


    override fun countMoodsByName(name: String, userID: String, callback: (Int) -> Unit) {
        val moodList = mutableListOf<Mood>()

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                moodList.clear()
                for (snapshot in dataSnapshot.children) {
                    val mood = snapshot.getValue(Mood::class.java)
                    if (mood != null && mood.name == name && mood.userID == userID) {
                        moodList.add(mood)
                    }
                }
                Log.e("MOODLIST", moodList.toString())
                callback(moodList.size)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("MoodDAO", databaseError.message)
            }
        }

        val reference = FirebaseDatabase.getInstance().getReference("Moods")
        reference.orderByChild("userID").equalTo(userID).addValueEventListener(listener)
    }




}