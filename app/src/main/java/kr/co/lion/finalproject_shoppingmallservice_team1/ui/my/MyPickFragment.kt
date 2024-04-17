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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.MY_PICK_TAB_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyPickBinding

class MyPickFragment : Fragment() {

    lateinit var fragmentMyPickBinding: FragmentMyPickBinding
    lateinit var navigationActivity: NavigationActivity

    // 프래그먼트 객체를 담을 변수
    var oldFragment:Fragment? = null
    var newFragment:Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyPickBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_pick, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        settingTabLayout()

        return fragmentMyPickBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MyPickFragment 가 실행될 때 하단바가 보이지 않도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()

        // MyPickFragment 가 제거될 때 하단바가 보이도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = true
    }

    // Toolbar 구성
    fun settingToolbar(){
        fragmentMyPickBinding.apply {
            toolbarMyPick.apply {
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    backProcess()
                }
                // 메뉴
                inflateMenu(R.menu.menu_my_pick)
            }
        }
    }

    // 뒤로가기 버튼
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

    // Tab 레이아웃 설정
    fun settingTabLayout(){
        fragmentMyPickBinding.apply {

            // 시작 시 Tab1 호출
            replaceFragment(MY_PICK_TAB_NAME.MY_PICK_TAB1_FRAGMENT, false, false, null)

            myPickTab.apply {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 탭이 선택되었을 때 호출되는 메서드
                        val position = tab?.position

                        when(position){
                            0 -> {
                                replaceFragment(MY_PICK_TAB_NAME.MY_PICK_TAB1_FRAGMENT, false, false, null)
                            }
                            1 -> {
                                replaceFragment(MY_PICK_TAB_NAME.MY_PICK_TAB2_FRAGMENT, false, false, null)
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

    fun replaceFragment(name: MY_PICK_TAB_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(50)

        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){

            // Tab1
            MY_PICK_TAB_NAME.MY_PICK_TAB1_FRAGMENT -> {
                newFragment = MyPickTab1Fragment()
            }
            // Tab2
            MY_PICK_TAB_NAME.MY_PICK_TAB2_FRAGMENT -> {
                newFragment = MyPickTab2Fragment()
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

            fragmentTransaction.replace(R.id.myPickTabView, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }
}