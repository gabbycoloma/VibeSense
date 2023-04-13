    package com.s12.mobdeve.coloma.caballero.vibesense
    import Home
    import android.os.Build
    import android.os.Bundle
    import android.util.Log
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.RadioButton
    import android.widget.Toast
    import androidx.annotation.RequiresApi
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.*
    import com.s12.mobdeve.coloma.caballero.vibesense.Model.Mood
    import com.s12.mobdeve.coloma.caballero.vibesense.Model.User
    import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentAddBinding
    import java.text.SimpleDateFormat
    import java.time.LocalDate
    import java.time.format.DateTimeFormatter
    import java.util.*

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"
    private lateinit var binding: FragmentAddBinding
    private var selectedMood : Int? = null
    private var moodName : String? = null
    private var moodDescription : String? = null
    private var currentDate: Date? = null
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://vibesense-9523f-default-rtdb.asia-southeast1.firebasedatabase.app")
    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser: User? = null

    class Add : Fragment() {
        // TODO: Rename and change types of parameters
        private var param1: String? = null
        private var param2: String? = null


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
            binding = FragmentAddBinding.inflate(inflater)
            return binding.root
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            firebaseAuth = FirebaseAuth.getInstance()

            val moodDAO = MoodDAOFireBaseImplementation(this.requireContext())

            val firebaseUser = firebaseAuth.currentUser
            val emailCheck = firebaseUser?.email
            Log.d("Current user email", emailCheck.toString())

            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
            binding.moodDate.text = currentDate.format(formatter)

            val id = databaseReference.push().key

            binding.rbMoods.setOnCheckedChangeListener { radioGroup, i ->
                val selectedRadioButton: RadioButton = view.findViewById(i)
                selectedMood = selectedRadioButton.text.toString().toIntOrNull()
                Log.d("Selected Mood", selectedMood.toString())
            }

            moodDescription = binding.etDesc.text.toString()
            binding.btnPost.setOnClickListener{
                if (selectedMood == null) {
                    Toast.makeText(requireContext(), "Please select a mood.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if(moodDescription == null){
                    Toast.makeText(requireContext(), "Please enter a description.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                getMoodName()
                val id = databaseReference.push().key
                Log.d("Current user email", emailCheck.toString())
                if (emailCheck == null) {
                    Toast.makeText(requireContext(), "User not logged in.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                getUserIdFromEmail(emailCheck) { userId ->
                    if (userId != null) {
                        var moodToAdd = Mood(id, getMoodName(), getMoodName(), binding.etDesc.text.toString(), currentDate.toString(), selectedMood, userId.toString())
                        moodDAO.addMood(moodToAdd)

                        Toast.makeText(requireContext(), "Mood entry added!", Toast.LENGTH_SHORT).show()
                        replaceFragment(Home())

                    } else {
                        Toast.makeText(requireContext(), "User not found.", Toast.LENGTH_SHORT).show()
                    }
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

        private fun getMoodName(): String {
            when (selectedMood) {
                5 -> moodName = "happy"
                4 -> moodName = "good"
                3 -> moodName = "neutral"
                2 -> moodName = "sad"
                1 -> moodName = "angry"
            }

            return moodName.toString()
        }

        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment Add.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                Add().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }

        private fun replaceFragment(fragment : Fragment){
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,fragment)
            fragmentTransaction.commit()
        }
    }