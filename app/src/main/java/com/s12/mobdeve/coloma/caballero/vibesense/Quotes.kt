import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.s12.mobdeve.coloma.caballero.vibesense.ZenQuotesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentQuotesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Quotes.newInstance] factory method to
 * create an instance of this fragment.
 */
class Quotes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Declare the binding property
    private lateinit var binding: FragmentQuotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using data binding
        binding = FragmentQuotesBinding.inflate(inflater)
        // Return the root view of the binding object
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ZenQuotesService::class.java)

        lifecycleScope.launch {
            try {
                val quotes = service.getRandomQuote()
                val randomQuote = quotes.random()
                withContext(Dispatchers.Main) {
                    // Update the text view using the binding object
                    binding.tvQuote.text = randomQuote.q
                    binding.tvAuthor.text = randomQuote.a
                }
            } catch (e: Exception) {
                // Handle any errors that may occur
                Log.e("QuotesFragment", "Error getting quote: ${e.message}")
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Quotes.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Quotes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
