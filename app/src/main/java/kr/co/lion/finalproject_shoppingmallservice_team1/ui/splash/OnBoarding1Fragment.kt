package kr.co.lion.finalproject_shoppingmallservice_team1.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentOnBoarding1Binding

class OnBoarding1Fragment : Fragment() {

    private lateinit var fragmentOnBoarding1Binding: FragmentOnBoarding1Binding
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentOnBoarding1Binding = FragmentOnBoarding1Binding.inflate(inflater)
        mainActivity = activity as MainActivity

        showPermissionBottomSheet()

        return fragmentOnBoarding1Binding.root
    }

    private fun showPermissionBottomSheet(){

        val permissionBottomFragment = PermissionBottomFragment()
        permissionBottomFragment.show(mainActivity.supportFragmentManager, "PermissionBottomSheet")
    }
}
