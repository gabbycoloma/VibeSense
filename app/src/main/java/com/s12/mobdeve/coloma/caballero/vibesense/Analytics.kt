package com.s12.mobdeve.coloma.caballero.vibesense

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.s12.mobdeve.coloma.caballero.vibesense.Model.User
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentAnalyticsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var currentUser: User? = null
private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://vibesense-9523f-default-rtdb.asia-southeast1.firebasedatabase.app")

/**
 * A simple [Fragment] subclass.
 * Use the [Analytics.newInstance] factory method to
 * create an instance of this fragment.
 */
class Analytics : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var moodDAO: MoodDAOFireBaseImplementation
    private lateinit var binding: FragmentAnalyticsBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moodCounts = ArrayList<Int>()
        firebaseAuth = FirebaseAuth.getInstance()
        moodDAO = MoodDAOFireBaseImplementation(requireActivity())

        val firebaseUser = firebaseAuth.currentUser
        val emailCheck = firebaseUser?.email
        Log.d("Current user email", emailCheck.toString())


        getUserIdFromEmail(emailCheck.toString()) {  userID->
            // Here, you can use the userId returned by the callback function
            moodDAO.countMoodsByName("happy", userID.toString()) { happyCount ->
                moodDAO.countMoodsByName("good", userID.toString()) { goodCount ->
                    moodDAO.countMoodsByName("neutral", userID.toString()) { neutralCount ->
                        moodDAO.countMoodsByName("sad", userID.toString()) { sadCount ->
                            moodDAO.countMoodsByName("angry", userID.toString()) { angryCount ->
                                binding.mood5Count.text = happyCount.toString()
                                binding.mood4Count.text = goodCount.toString()
                                binding.mood3Count.text = neutralCount.toString()
                                binding.mood2Count.text = sadCount.toString()
                                binding.mood1Count.text = angryCount.toString()
                                var happyCount: String? = binding.mood5Count.text.toString()
                                var goodCount: String? = binding.mood5Count.text.toString()
                                var neutralCount: String? = binding.mood5Count.text.toString()
                                var sadCount: String? = binding.mood5Count.text.toString()
                                var angryCount: String? = binding.mood5Count.text.toString()

                                val moodCounts = mapOf(
                                    "happy" to happyCount!!.toInt(),
                                    "good" to goodCount!!.toInt(),
                                    "neutral" to neutralCount!!.toInt(),
                                    "sad" to sadCount!!.toInt(),
                                    "angry" to angryCount!!.toInt()
                                )

                                val topMoodName = moodCounts.maxByOrNull { it.value }?.key
                                binding.tvtopMoodName.text = topMoodName
                                when {
                                    topMoodName.equals("happy", true) -> {
                                        binding.ivTopMood.setImageResource(R.drawable.mood1)
                                    }
                                    topMoodName.equals("good", true) -> {
                                        binding.ivTopMood.setImageResource(R.drawable.mood2)
                                    }
                                    topMoodName.equals("neutral", true) -> {
                                        binding.ivTopMood.setImageResource(R.drawable.mood3)
                                    }
                                    topMoodName.equals("sad", true) -> {
                                        binding.ivTopMood.setImageResource(R.drawable.mood4)
                                    }
                                    topMoodName.equals("angry", true) -> {
                                        binding.ivTopMood.setImageResource(R.drawable.mood5)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Analytics().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getUserIdFromEmail(email: String, callback: (String?) -> Unit) {
        val userReference = databaseReference.child("Users")
        userReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        val userId = userSnapshot.key
                        callback(userId)
                        return
                    }
                }
                callback(null)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }


}
