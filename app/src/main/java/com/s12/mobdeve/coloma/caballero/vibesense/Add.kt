    package com.s12.mobdeve.coloma.caballero.vibesense

    import android.os.Bundle
    import android.util.Log
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.RadioButton
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity
    import com.s12.mobdeve.coloma.caballero.vibesense.databinding.ActivityLandingBinding
    import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentAddBinding
    import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentQuotesBinding
    import org.w3c.dom.Text
    import java.text.SimpleDateFormat
    import java.util.*

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"
    private lateinit var binding: FragmentAddBinding
    private var selectedMood : Int? = null
    private var moodName : String? = null
    private var moodDescription : String? = null
    private var currentDate: String? = null

    /**
     * A simple [Fragment] subclass.
     * Use the [Add.newInstance] factory method to
     * create an instance of this fragment.
     */
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

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            currentDate = SimpleDateFormat("MMMM d, yyyy").format(Date())
            binding.moodDate.text = currentDate

            binding.rbMoods.setOnCheckedChangeListener { radioGroup, i ->
                val selectedRadioButton: RadioButton = view.findViewById(i)
                selectedMood = selectedRadioButton.text.toString().toIntOrNull()
                Log.d("Selected Mood", selectedMood.toString())
            }


            binding.btnPost.setOnClickListener{
                moodDescription = binding.etDesc.text.toString()
                setMoodName()
                Log.d("Selected Mood", selectedMood.toString())
                Log.d("Mood Name", moodName.toString())
                Log.d("Description ", moodDescription!!)

                replaceFragment(Home())
            }
        }
        private fun setMoodName() {
            if (selectedMood == 5) {
                moodName = "happy"
            }else if(selectedMood == 4)
                moodName = "good"
            else if(selectedMood == 3)
                moodName = "neutral"
            else if(selectedMood == 2)
                moodName = "sad"
            else if(selectedMood == 1)
                moodName = "angry"
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