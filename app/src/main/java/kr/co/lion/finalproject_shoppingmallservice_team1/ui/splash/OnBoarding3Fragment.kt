package kr.co.lion.finalproject_shoppingmallservice_team1.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.splash.MainActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentOnBoarding3Binding

class OnBoarding3Fragment : Fragment() {

    private lateinit var fragmentOnBoarding3Binding: FragmentOnBoarding3Binding
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentOnBoarding3Binding = FragmentOnBoarding3Binding.inflate(inflater)
        mainActivity = activity as MainActivity

        return fragmentOnBoarding3Binding.root
    }

}