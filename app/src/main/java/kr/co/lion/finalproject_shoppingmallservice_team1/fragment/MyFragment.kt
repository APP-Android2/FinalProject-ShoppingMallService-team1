package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.MY_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.Tools
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyBinding

class MyFragment : Fragment() {

    lateinit var fragmentMyBinding: FragmentMyBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingMyProfile()
        settingClickEventTextView()

        return fragmentMyBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyBinding.apply {
            toolbarMy.apply {
                // 메뉴
                inflateMenu(R.menu.menu_my)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 장바구니
                        R.id.menuMyShopping -> {

                        }
                    }

                    true
                }
            }
        }
    }

    // 프로필 프래그먼트
    fun settingMyProfile(){
        fragmentMyBinding.apply {
            // 프로필 클릭 시
            myProfile.setOnClickListener {
                // MyProfile 프래그먼트 실행
                navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.MY_PROFILE_FRAGMENT, true, true, null)
            }
        }
    }

    // TextView 항목 클릭 시 이벤트
    fun settingClickEventTextView(){
        fragmentMyBinding.apply {
            /*  상단 메뉴 탭 구성  */
            // 회원권
            textViewMyMembership.setOnClickListener {
                // MyMembershipFragment 실행
                navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.MY_MEMBERSHIP_FRAGMENT, true, true, null)
            }
            // 리뷰 관리

            // 찜

            /*  하단 메뉴 탭 구성  */
            // 공지/이벤트
            myNotification.setOnClickListener {
                // MyNotificationFragment 실행
                navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.MY_NOTIFICATION_FRAGMENT, true, true, null)
            }
            // 결제 내역
            myPayment.setOnClickListener {
                // MyPaymentFragment 실행
                navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.MY_PAYMENT_FRAGMENT, true, true, null)
            }

            // 고객 센터

            // FAQ
            myFAQ.setOnClickListener {
                // MyFAQFragment 실행
                navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.MY_FAQ_FRAGMENT, true, true, null)
            }
            // 설정
            mySetting.setOnClickListener {
                // MySettingFragment 실행
                navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.MY_SETTING_FRAGMENT, true, true, null)
            }
        }
    }
}