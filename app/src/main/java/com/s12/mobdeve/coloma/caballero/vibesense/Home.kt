package com.s12.mobdeve.coloma.caballero.vibesense

import MoodViewModel
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s12.mobdeve.coloma.caballero.vibesense.Adapter.MainAdapter
import com.s12.mobdeve.coloma.caballero.vibesense.Model.Mood
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentHomeBinding

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var recyclerView: RecyclerView
private lateinit var mainAdapter: MainAdapter
private lateinit var moodList : ArrayList<Mood> //for testing purposes
private lateinit var viewModel: MoodViewModel
private lateinit var binding: FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var btnDatePicker: Button
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        binding = FragmentHomeBinding.bind(view)
        val btnDatePicker = binding.btnDate

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayofMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_YEAR, dayofMonth)
            updateLable(myCalendar)
        }

        btnDatePicker.setOnClickListener{
            DatePickerDialog(this.requireContext(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        moodList = ArrayList()



        mainAdapter = MainAdapter()
        recyclerView.adapter = mainAdapter

        viewModel = ViewModelProvider(this).get(MoodViewModel::class.java)

        viewModel.allMoods.observe(viewLifecycleOwner, Observer {
            mainAdapter.updateMoodList(it)
        })

    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        btnDatePicker.setText(sdf.format(myCalendar.time))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}