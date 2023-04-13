import MoodViewModel
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s12.mobdeve.coloma.caballero.vibesense.Adapter.MainAdapter
import com.s12.mobdeve.coloma.caballero.vibesense.Model.Mood
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentHomeBinding

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var recyclerView: RecyclerView
private lateinit var mainAdapter: MainAdapter
private lateinit var moodList : ArrayList<Mood>
private lateinit var viewModel: MoodViewModel
private lateinit var binding: FragmentHomeBinding


class Home : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var myCalendar: Calendar

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

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val txtDate: TextView = binding.txtDate
        val btnFilter: ImageView = binding.btnFilter
        val llFilter: LinearLayoutCompat = binding.llFilter

        moodList = ArrayList()
        mainAdapter = MainAdapter()
        recyclerView.adapter = mainAdapter

        val myCalendar = Calendar.getInstance()
        updateLabel(myCalendar)

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)

            val monthHandle = myCalendar.get(Calendar.MONTH)
            val yearHandle = myCalendar.get(Calendar.YEAR)

            viewModel = ViewModelProvider(this).get(MoodViewModel::class.java)

            viewModel.allMoods.observe(viewLifecycleOwner, Observer {
                mainAdapter.updateMoodList(monthHandle, yearHandle, it)
            })
        }

        llFilter.setOnClickListener{
            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }


    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.txtDate.text = sdf.format(myCalendar.time)
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