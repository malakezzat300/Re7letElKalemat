import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.View.OnboardingActivity

class OnboardingFragment : Fragment() {

    private var page: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_page1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        page = arguments?.getInt("page") ?: 0
        when (page) {
            0 -> setupPage1(view)
            1 -> setupPage2(view)
            2 -> setupPage3(view)
        }
    }

    private fun setupPage1(view: View) {
        val nextButton: Button = view.findViewById(R.id.nextButton1)
        nextButton.setOnClickListener {
            (activity as? OnboardingActivity)?.navigateToNextPage()
        }
    }

    private fun setupPage2(view: View) {
        val nextButton: Button = view.findViewById(R.id.nextButton2)
        nextButton.setOnClickListener {
            (activity as? OnboardingActivity)?.navigateToNextPage()
        }
    }

    private fun setupPage3(view: View) {
        val startButton: Button = view.findViewById(R.id.startButton)
        startButton.setOnClickListener {
            (activity as? OnboardingActivity)?.startMainActivity()
        }
    }

    companion object {
        fun newInstance(page: Int): OnboardingFragment {
            val fragment = OnboardingFragment()
            val args = Bundle()
            args.putInt("page", page)
            fragment.arguments = args
            return fragment
        }
    }
}
