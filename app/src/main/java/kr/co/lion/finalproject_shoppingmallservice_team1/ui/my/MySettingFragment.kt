package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
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
        handleBackPress()

        return fragmentMySettingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MySettingFragment 가 실행될 때 하단바가 보이지 않도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()

        // MySettingFragment 가 제거될 때 하단바가 보이도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = true
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
    private fun backProcess(){
        SystemClock.sleep(200)
        parentFragmentManager.popBackStack()
    }

    // 뒤로가기 처리(단말기)
    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로가기
                backProcess()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}