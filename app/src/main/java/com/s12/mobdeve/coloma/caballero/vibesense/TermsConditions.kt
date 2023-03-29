import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.s12.mobdeve.coloma.caballero.vibesense.More
import com.s12.mobdeve.coloma.caballero.vibesense.R
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentMoreBinding
import com.s12.mobdeve.coloma.caballero.vibesense.databinding.FragmentTermsConditionsBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TermsConditions : Fragment() {
    private lateinit var binding: FragmentTermsConditionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                replaceFragment(More())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermsConditionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener{
            replaceFragment(More())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TermsConditions().apply {
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
