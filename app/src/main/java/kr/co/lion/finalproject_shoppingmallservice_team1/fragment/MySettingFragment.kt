package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMySettingBinding

class MySettingFragment : Fragment() {

    lateinit var fragmentMySettingBinding: FragmentMySettingBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMySettingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_setting, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()

        return fragmentMySettingBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMySettingBinding.apply {
            toolbarMySetting.apply {
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    backProcess()
                }
            }
        }
    }

    // 뒤로가기 처리
    fun backProcess(){
        SystemClock.sleep(200)
        parentFragmentManager.popBackStack()
    }
}