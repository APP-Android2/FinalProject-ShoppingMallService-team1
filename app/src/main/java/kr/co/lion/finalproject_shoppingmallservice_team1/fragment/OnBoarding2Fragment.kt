package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.finalproject_shoppingmallservice_team1.MainActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentOnBoarding2Binding

class OnBoarding2Fragment : Fragment() {

    private lateinit var fragmentOnBoarding2Binding: FragmentOnBoarding2Binding
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentOnBoarding2Binding = FragmentOnBoarding2Binding.inflate(inflater)
        mainActivity = activity as MainActivity

        return fragmentOnBoarding2Binding.root
    }

}