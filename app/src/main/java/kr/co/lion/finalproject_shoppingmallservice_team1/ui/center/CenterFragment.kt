package kr.co.lion.finalproject_shoppingmallservice_team1.ui.center

import android.os.Bundle
import android.os.SystemClock
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.CENTER_TAB_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCenterBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.center.viewmodel.CenterViewModel
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity

class CenterFragment : Fragment() {

    lateinit var fragmentCenterBinding: FragmentCenterBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var centerViewModel: CenterViewModel

    // 프래그먼트 객체를 담을 변수
    var oldFragment:Fragment? = null
    var newFragment:Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCenterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_center, container, false)
        navigationActivity = activity as NavigationActivity
        centerViewModel = ViewModelProvider(this).get(CenterViewModel::class.java)
        fragmentCenterBinding.centerViewModel = centerViewModel
        fragmentCenterBinding.lifecycleOwner = this

        // 네비게이션 아이템의 선택 상태 변경
        navigationActivity.activityNavigationBinding.bottomNavigationView.menu.findItem(R.id.fragment_center).isChecked = true
        // 아이템의 색상 변경
        navigationActivity.updateIconColors(R.id.fragment_center)

        val category = arguments?.getString("category")
        when(category){
            "health" -> {
                replaceFragment(CENTER_TAB_NAME.CENTER_TAB1_FRAGMENT, false, false, null)
                fragmentCenterBinding.centerTab.getTabAt(0)?.select()
            }
            "pilates" -> {
                replaceFragment(CENTER_TAB_NAME.CENTER_TAB2_FRAGMENT, false, false, null)
                fragmentCenterBinding.centerTab.getTabAt(1)?.select()
            }
            "swim" -> {
                replaceFragment(CENTER_TAB_NAME.CENTER_TAB3_FRAGMENT, false, false, null)
                fragmentCenterBinding.centerTab.getTabAt(2)?.select()
            }
            "dailyTicket" -> {
                centerViewModel.chipChecked.value = true
            }
            "sale" -> {
                centerViewModel.chipChecked2.value = true
            }
            "aroundCenter" -> {
                fragmentCenterBinding.chipDistance.text = "거리순"
            }
        }

        settingToolbar()
        settingTabLayout()
        settingChip()

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

    // chip 설정
    fun settingChip(){
        fragmentCenterBinding.apply {
            chipDistance.apply {
                setOnClickListener {
                    val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)

                    val popup = PopupMenu(contextWrapper, this)
                    popup.inflate(R.menu.menu_center_chip)

                    popup.setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.menuItemCenterDistance -> {
                                text = "거리순"

                            }
                            R.id.menuItemCenterRecent -> {
                                text = "최신순"
                            }
                        }
                        true
                    }
                    popup.show()
                }
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
                newFragment = CenterTab2Fragment()
            }
            // Tab3
            CENTER_TAB_NAME.CENTER_TAB3_FRAGMENT -> {
                newFragment = CenterTab3Fragment()
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

