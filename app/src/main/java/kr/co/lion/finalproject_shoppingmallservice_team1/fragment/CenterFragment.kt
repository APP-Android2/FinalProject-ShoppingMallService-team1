package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.CENTER_TAB_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.MY_REVIEW_TAB_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCenterBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.my.MyReviewTab1Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.my.MyReviewTab2Fragment

class CenterFragment : Fragment() {

    lateinit var fragmentCenterBinding: FragmentCenterBinding
    lateinit var navigationActivity: NavigationActivity

    // 프래그먼트 객체를 담을 변수
    var oldFragment:Fragment? = null
    var newFragment:Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCenterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_center, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingTabLayout()

        return fragmentCenterBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentCenterBinding.apply {
            toolbarCenter.apply {
                inflateMenu(R.menu.menu_center)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 장바구니
                        R.id.menuCenterShopping -> {

                        }
                    }
                    true
                }
            }
        }
    }

    // Tab 레이아웃 설정
    fun settingTabLayout(){
        fragmentCenterBinding.apply {

            // 시작 시 Tab1 호출
            replaceFragment(CENTER_TAB_NAME.CENTER_TAB1_FRAGMENT, false, false, null)

            centerTab.apply {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 탭이 선택되었을 때 호출되는 메서드
                        val position = tab?.position

                        when(position){
                            0 -> {
                                replaceFragment(CENTER_TAB_NAME.CENTER_TAB1_FRAGMENT, false, false, null)
                            }
                            1 -> {
                                replaceFragment(CENTER_TAB_NAME.CENTER_TAB2_FRAGMENT, false, false, null)
                            }
                            2 -> {
                                replaceFragment(CENTER_TAB_NAME.CENTER_TAB3_FRAGMENT, false, false, null)
                            }
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        // 선택이 해제된 탭의 경우 처리할 내용

                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        // 이미 선택된 탭이 다시 선택된 경우 처리할 내용
                    }
                })
            }
        }
    }

    fun replaceFragment(name: CENTER_TAB_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(50)

        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){

            // Tab1
            CENTER_TAB_NAME.CENTER_TAB1_FRAGMENT -> {
                newFragment = CenterTab1Fragment()
            }
            // Tab2
            CENTER_TAB_NAME.CENTER_TAB2_FRAGMENT -> {

            }
            // Tab3
            CENTER_TAB_NAME.CENTER_TAB3_FRAGMENT -> {

            }
        }

        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){

            // 애니메이션 설정
            if(isAnimate == true){

                if(oldFragment != null){
                    // old에서 new가 새롭게 보여질 때 old의 애니메이션
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    // new에서 old로 되돌아갈때 old의 애니메이션
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }

                // old에서 new가 새롭게 보여질 때 new의 애니메이션
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                // new에서 old로 되돌아갈때 new의 애니메이션
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }

            fragmentTransaction.replace(R.id.centerTabView, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }
}

