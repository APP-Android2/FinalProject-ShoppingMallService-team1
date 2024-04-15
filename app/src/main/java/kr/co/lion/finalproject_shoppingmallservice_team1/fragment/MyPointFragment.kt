package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import kr.co.lion.finalproject_shoppingmallservice_team1.MY_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.ShoppingCartActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyPointBinding

class MyPointFragment : Fragment() {

    lateinit var fragmentMyPointBinding: FragmentMyPointBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyPointBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_point, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        settingClickEventTextView()

        return fragmentMyPointBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyPointBinding.apply {
            toolbarMyPoint.apply {
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

    // TextView 항목 클릭 시 이벤트
    fun settingClickEventTextView(){
        fragmentMyPointBinding.apply {
            // 리뷰 작성
            myPointWriteReview.setOnClickListener {

            }

            // 회원권 구매
            myPointBuyMembership.setOnClickListener {

            }

            // 커뮤니티 글 작성
            myPointWriteCommunity.setOnClickListener {

            }

            // 댓글 작성
            myPointWriteComment.setOnClickListener {

            }
        }
    }
}