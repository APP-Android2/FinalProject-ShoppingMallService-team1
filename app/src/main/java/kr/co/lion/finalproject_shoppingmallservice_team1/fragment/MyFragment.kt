package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.AlarmActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.MY_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyBinding

class MyFragment : Fragment() {

    lateinit var fragmentMyBinding: FragmentMyBinding
    lateinit var navigationActivity: NavigationActivity

    // 프래그먼트의 주소값을 담을 프로퍼티
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingMyProfile()
        settingMyBenefit()
        settingClickEventTextView()

        return fragmentMyBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyBinding.apply {
            toolbarMy.apply {
                // 알림
                setNavigationIcon(R.drawable.notifications)
                setNavigationOnClickListener {
                    val intent = Intent(navigationActivity, AlarmActivity::class.java)
                    startActivity(intent)
                }
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
                replaceFragment(MY_FRAGMENT_NAME.MY_PROFILE_FRAGMENT, true, true, null)
            }
        }
    }

    // 포인트, 쿠폰
    fun settingMyBenefit(){
        fragmentMyBinding.apply {
            // 포인트
            myPointCardView.setOnClickListener {
                // MyPointFragment 실행
                replaceFragment(MY_FRAGMENT_NAME.MY_POINT_FRAGMENT, true, true, null)
            }

            // 쿠폰
            myCouponCardView.setOnClickListener {
                // MyCouponFragment 실행
                replaceFragment(MY_FRAGMENT_NAME.MY_COUPON_FRAGMENT, true, true, null)
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
                replaceFragment(MY_FRAGMENT_NAME.MY_MEMBERSHIP_FRAGMENT, true, true, null)
            }
            // 리뷰 관리
            textViewMyReview.setOnClickListener {
                // MyReviewFragment 실행
                replaceFragment(MY_FRAGMENT_NAME.MY_REVIEW_FRAGMENT, true, true, null)
            }
            // 찜
            textViewMyPick.setOnClickListener {
                // MyPickFragment 실행
                replaceFragment(MY_FRAGMENT_NAME.MY_PICK_FRAGMENT, true, true, null)
            }

            /*  하단 메뉴 탭 구성  */
            // 방문상담신청내역
            myVisitConsulting.setOnClickListener {
                // MyVisitConsultationFragment 실행
                replaceFragment(MY_FRAGMENT_NAME.MY_VISIT_CONSULTATION_FRAGMENT, true, true, null)
            }
            // 결제 내역
            myPayment.setOnClickListener {
                // MyPaymentFragment 실행
                replaceFragment(MY_FRAGMENT_NAME.MY_PAYMENT_FRAGMENT, true, true, null)
            }

            // 고객 센터

            // FAQ
            myFAQ.setOnClickListener {
                // MyFAQFragment 실행
                replaceFragment(MY_FRAGMENT_NAME.MY_FAQ_FRAGMENT, true, true, null)
            }
            // 설정
            mySetting.setOnClickListener {
                // MySettingFragment 실행
                replaceFragment(MY_FRAGMENT_NAME.MY_SETTING_FRAGMENT, true, true, null)
            }
        }
    }

    // 프래그먼트 이동
    fun replaceFragment(name: MY_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){

            // 프로필 설정
            MY_FRAGMENT_NAME.MY_PROFILE_FRAGMENT -> {
                newFragment = MyProfileFragment()
            }

            // 포인트
            MY_FRAGMENT_NAME.MY_POINT_FRAGMENT -> {
                newFragment = MyPointFragment()
            }

            // 쿠폰
            MY_FRAGMENT_NAME.MY_COUPON_FRAGMENT -> {
                newFragment = MyCouponFragment()
            }

            // 회원권
            MY_FRAGMENT_NAME.MY_MEMBERSHIP_FRAGMENT -> {
                newFragment = MyMembershipFragment()
            }

            // 리뷰 관리
            MY_FRAGMENT_NAME.MY_REVIEW_FRAGMENT -> {
                newFragment = MyReviewFragment()
            }

            // 찜
            MY_FRAGMENT_NAME.MY_PICK_FRAGMENT -> {
                newFragment = MyPickFragment()
            }

            // 방문상담 신청 내역
            MY_FRAGMENT_NAME.MY_VISIT_CONSULTATION_FRAGMENT -> {
                newFragment = MyVisitConsultationFragment()
            }

            // 결제 내역
            MY_FRAGMENT_NAME.MY_PAYMENT_FRAGMENT -> {
                newFragment = MyPaymentFragment()
            }

            // 고객 센터

            // FAQ
            MY_FRAGMENT_NAME.MY_FAQ_FRAGMENT -> {
                newFragment = MyFAQFragment()
            }

            // 설정
            MY_FRAGMENT_NAME.MY_SETTING_FRAGMENT -> {
                newFragment = MySettingFragment()
            }
        }

        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){
            if(isAnimate == true){

                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }
            fragmentTransaction.replace(R.id.fragmentMy, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }
}