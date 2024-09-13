import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.View.OnboardingActivity
import com.malakezzat.re7letelkalemat.databinding.FragmentOnboardingPage1Binding
import com.malakezzat.re7letelkalemat.databinding.FragmentOnboardingPage2Binding
import com.malakezzat.re7letelkalemat.databinding.FragmentOnboardingPage3Binding

class OnboardingFragment : Fragment() {

    private var page: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when (page) {
            0 -> DataBindingUtil.inflate<FragmentOnboardingPage1Binding>(inflater, R.layout.fragment_onboarding_page1, container, false).root
            1 -> DataBindingUtil.inflate<FragmentOnboardingPage2Binding>(inflater, R.layout.fragment_onboarding_page2, container, false).root
            2 -> DataBindingUtil.inflate<FragmentOnboardingPage3Binding>(inflater, R.layout.fragment_onboarding_page3, container, false).root
            else -> DataBindingUtil.inflate<FragmentOnboardingPage1Binding>(inflater, R.layout.fragment_onboarding_page1, container, false).root
        }
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
        val binding = DataBindingUtil.bind<FragmentOnboardingPage1Binding>(view)
        binding?.nextButton1?.setOnClickListener {
            (activity as? OnboardingActivity)?.navigateToNextPage()
        }
    }

    private fun setupPage2(view: View) {
        val binding = DataBindingUtil.bind<FragmentOnboardingPage2Binding>(view)
        binding?.nextButton2?.setOnClickListener {
            (activity as? OnboardingActivity)?.navigateToNextPage()
        }
    }

    private fun setupPage3(view: View) {
        val binding = DataBindingUtil.bind<FragmentOnboardingPage3Binding>(view)
        binding?.startButton?.setOnClickListener {
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