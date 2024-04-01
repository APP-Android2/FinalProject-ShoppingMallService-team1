package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.MY_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.Navigation_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
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
        settingEventTextView()

        /*


        상의 해야할 내용

            알림버튼을 눌렀을 때와 공지/이벤트를 눌렀을 때 화면을 나눌것인가
            만약 나눈다면..
            알림 = 개인마다 다른 내용 ex) 트레이너 메세지 예약 알림 등
            공지/이벤트 = 앱에서 올리는 공지사항 및 이벤트
            나누지 않는다면..

         */

        return fragmentMyBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyBinding.apply {
            toolbarMy.apply {
                // 타이틀
                title = "마이페이지"
                // 메뉴
                inflateMenu(R.menu.menu_my)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 알림
                        R.id.menuMyNotification -> {

                        }
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
                navigationActivity.replaceFragment(Navigation_FRAGMENT_NAME.MY_PROFILE_FRAGMENT, true, true, null)
            }
        }
    }

    // TextView 항목 클릭 시 이벤트
    fun settingEventTextView(){
        fragmentMyBinding.apply {
            // 공지/이벤트
            myNotification.setOnClickListener {
                navigationActivity.replaceFragment(Navigation_FRAGMENT_NAME.MY_NOTIFICATION_FRAGMENT, true, true, null)
            }
            // 결제 내역

            // 고객 센터

            // FAQ

            // 설정
        }
    }
}