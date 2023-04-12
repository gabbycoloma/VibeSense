package com.s12.mobdeve.coloma.caballero.vibesense.Repository

import android.renderscript.Sampler.Value
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.s12.mobdeve.coloma.caballero.vibesense.Model.Mood

class MoodRepository {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Moods")

    @Volatile private var INSTANCE: MoodRepository ?= null

    fun getInstance() : MoodRepository{
        return INSTANCE ?: synchronized(this){

            val instance = MoodRepository()
            INSTANCE = instance
            instance
        }
    }


    fun loadMood(moodList: MutableLiveData<List<Mood>>){
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try{
                    val _moodList: List<Mood> = snapshot.children.map{ dataSnapshot ->

                        dataSnapshot.getValue(Mood::class.java)!!


                    }
                    moodList.postValue(_moodList)

                }catch (e : Exception){


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }


    fun loadMoodByUserId(userID: String, moodList: MutableLiveData<List<Mood>>) {
        val query = databaseReference.orderByChild("userID").equalTo(userID)
        query.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try{
                    val _moodList: List<Mood> = snapshot.children.map{ dataSnapshot ->

                        dataSnapshot.getValue(Mood::class.java)!!


                    }
                    moodList.postValue(_moodList)

                }catch (e : Exception){


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }

}