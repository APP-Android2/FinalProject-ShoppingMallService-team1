package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
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
        handleBackPress()

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
                        backProcess()
                    }
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