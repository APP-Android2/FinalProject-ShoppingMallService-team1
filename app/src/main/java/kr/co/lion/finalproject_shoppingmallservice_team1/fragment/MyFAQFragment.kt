package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyFAQBinding

class MyFAQFragment : Fragment() {

    lateinit var fragmentMyFAQBinding: FragmentMyFAQBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyFAQBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_f_a_q, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()

        return fragmentMyFAQBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyFAQBinding.apply {
            fragmentMyFAQBinding.apply {
                toolbarMyFAQ.apply {
                    // 뒤로가기
                    setNavigationIcon(R.drawable.arrow_back)
                    setNavigationOnClickListener {
                        navigationActivity.removeFragment(NAVIGATION_FRAGMENT_NAME.MY_FAQ_FRAGMENT)
                    }
                }
            }
        }
    }
}