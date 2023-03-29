package com.s12.mobdeve.coloma.caballero.vibesense

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentMoreBinding
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentViewMoodBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewMood.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewMood : Fragment() {
    private lateinit var binding: FragmentViewMoodBinding
    private var emoji: Int? = null
    private var name: String? = null
    private var description: String? = null
    private var date: String? = null

    companion object{
        const val emojiKey : String = "EMOJI_KEY"
        const val nameKey : String = "NAME_KEY"
        const val descriptionKey : String = "DESCRIPTION_KEY"
        const val dateKey : String = "DATE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            emoji = it.getInt(emojiKey) // Get the Int value
            name = it.getString(nameKey)
            description = it.getString(descriptionKey)
            date = it.getString(dateKey)
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                replaceFragment(Home())
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewMoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the emoji, name, description, and date in the UI
        binding.moodEmoji.setImageResource(emoji!!) // Convert the Int value to an Int resource ID
        binding.moodName.text = name
        binding.moodDesc.text = description
        binding.moodDate.text = date

        binding.btnBack.setOnClickListener{
            replaceFragment(Home())
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}


