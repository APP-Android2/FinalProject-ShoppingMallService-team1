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
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {

    lateinit var fragmentMyProfileBinding: FragmentMyProfileBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()

        return fragmentMyProfileBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyProfileBinding.apply {
            toolbarMyProfile.apply {
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    navigationActivity.removeFragment(NAVIGATION_FRAGMENT_NAME.MY_PROFILE_FRAGMENT)
                }
                // 메뉴
                inflateMenu(R.menu.menu_my_profile)
            }
        }
    }
}