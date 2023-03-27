import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.s12.mobdeve.coloma.caballero.vibesense.Quote
import com.s12.mobdeve.coloma.caballero.vibesense.ZenQuotesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentQuotesBinding
import java.text.SimpleDateFormat
import java.util.*

class Quotes : Fragment() {

    private lateinit var binding: FragmentQuotesBinding
    private var randomQuote: Quote? = null
    private var currentDate: String? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textToSpeech: TextToSpeech


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuotesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        // Get the last displayed quote date from SharedPreferences
        val lastDisplayedDate = sharedPreferences.getString("lastDisplayedDate", "")

        // Get the current date
        currentDate = SimpleDateFormat("MMMM d, yyyy").format(Date())

        // If the last displayed date does not match the current date, fetch a new quote
        if (lastDisplayedDate != currentDate) {
            fetchNewQuote()
        } else {
            // Otherwise, display the quote from SharedPreferences
            val quoteText = sharedPreferences.getString("quoteText", "")
            val authorText = sharedPreferences.getString("authorText", "")
            binding.tvQuote.text = quoteText
            binding.tvAuthor.text = authorText
            binding.quoteDate.text = currentDate
        }

        // Initialize the TextToSpeech object
        textToSpeech = TextToSpeech(requireContext()) { status ->
            if (status != TextToSpeech.ERROR) {
                // Set the language of the TextToSpeech object
                textToSpeech.language = Locale.US
            } else {
                Log.e("QuotesFragment", "Error initializing TextToSpeech")
            }
        }

        binding.ttsButton.setOnClickListener {
            val quoteText = sharedPreferences.getString("quoteText", "")
            textToSpeech.speak(quoteText, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
    override fun onDestroyView() {
        // Release the TextToSpeech object when the fragment is destroyed
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroyView()
    }
    private fun fetchNewQuote() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ZenQuotesService::class.java)

        lifecycleScope.launch {
            try {
                val quotes = service.getRandomQuote()
                randomQuote = quotes.random()
                updateQuoteViews()
                // Store the new quote and current date in SharedPreferences
                with(sharedPreferences.edit()) {
                    putString("quoteText", randomQuote?.q)
                    putString("authorText", randomQuote?.a)
                    putString("lastDisplayedDate", currentDate)
                    apply()
                }
            } catch (e: Exception) {
                Log.e("QuotesFragment", "Error getting quote: ${e.message}")
            }
        }
    }

    private fun updateQuoteViews() {
        binding.tvQuote.text = randomQuote?.q ?: ""
        binding.tvAuthor.text = randomQuote?.a ?: ""
        binding.quoteDate.text = currentDate
    }

    override fun onResume() {
        super.onResume()
        // Update the date in case it has changed since the fragment was last shown
        currentDate?.let {
            binding.quoteDate.text = it
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Quotes()
    }
}
