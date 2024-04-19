package kr.co.lion.finalproject_shoppingmallservice_team1.ui.center

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.CENTER_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadCenterBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.shoppingcart.ShoppingCartActivity


class ReadCenterFragment : Fragment() {

    lateinit var fragmentReadCenterBinding: FragmentReadCenterBinding
    lateinit var readCenterActivity: ReadCenterActivity

    // 프래그먼트 객체를 담을 변수
    var oldFragment:Fragment? = null
    var newFragment:Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadCenterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_center, container, false)
        fragmentReadCenterBinding.lifecycleOwner = this

        readCenterActivity = activity as ReadCenterActivity

        settingToolbarReadTrainer()
        onOffsetChanged()
        settingTabLayout()

        replaceFragment(CENTER_FRAGMENT_NAME.READ_CENTER_TAB1_FRAGMENT, false, false, null)


        return fragmentReadCenterBinding.root
    }

    /**
     * 함수 정리 (작성 순서)
     * 1. 툴바 설정 (settingToolbarReadTrainer())
     * 2. AppBarLayout 상태 설정 (onOffsetChanged())
     * 3. Tab 레이아웃 설정 (settingTabLayout())
     * 4. Fragment 교체 설정 (정보, 리뷰, 상담 탭 위치) (replaceFragment())
     */


    fun settingToolbarReadTrainer(){
        fragmentReadCenterBinding.apply {
            toolbarReadTrainer.apply {
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    readCenterActivity.finish()
                }

                inflateMenu(R.menu.menu_trainer)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemTrainerShopping -> {
                            val shoppingCartIntent = Intent(readCenterActivity, ShoppingCartActivity::class.java)
                            startActivity(shoppingCartIntent)
                        }
                    }
                    true
                }
            }
        }
    }

    fun onOffsetChanged(){
        fragmentReadCenterBinding.apply {
            appbarReadTrainer.apply {
                val onOffsetChangedListener = OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    // 스크롤 위치에 따른 작업 수행
                    if (Math.abs(verticalOffset) >= appBarLayout!!.totalScrollRange) {
                        // AppBarLayout이 완전히 축소된 상태
                        toolbarReadTrainerTitle.visibility = View.VISIBLE
                        toolbarReadTrainer.setBackgroundColor(Color.WHITE)

                    } else {
                        // AppBarLayout이 축소 되지 않은 상태
                        toolbarReadTrainerTitle.visibility = View.GONE
                        toolbarReadTrainer.setBackgroundColor(Color.TRANSPARENT)
                    }
                }
                appbarReadTrainer.addOnOffsetChangedListener(onOffsetChangedListener)
            }
        }
    }


    fun settingTabLayout(){
        fragmentReadCenterBinding.apply {
            trainerInfoTab.apply {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 탭이 선택되었을 때 호출되는 메서드
                        val position = tab?.position

                        when(position){
                            0 -> {
                                replaceFragment(CENTER_FRAGMENT_NAME.READ_CENTER_TAB1_FRAGMENT, false, false, null)
                            }
                            1 -> {
                                replaceFragment(CENTER_FRAGMENT_NAME.READ_CENTER_TAB2_FRAGMENT, false, false, null)
                            }
                            2 -> {
                                replaceFragment(CENTER_FRAGMENT_NAME.READ_CENTER_TAB3_FRAGMENT, false, false, null)
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

    fun replaceFragment(name: CENTER_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(100)

        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            CENTER_FRAGMENT_NAME.READ_CENTER_FRAGMENT -> {
                newFragment = ReadCenterFragment()
            }
            CENTER_FRAGMENT_NAME.READ_CENTER_TAB1_FRAGMENT -> {
                newFragment = ReadCenterTab1Fragment()
            }
            CENTER_FRAGMENT_NAME.READ_CENTER_TAB2_FRAGMENT -> {
                newFragment = ReadCenterTab2Fragment()
            }
            CENTER_FRAGMENT_NAME.READ_CENTER_TAB3_FRAGMENT -> {
                newFragment = ReadCenterTab3Fragment()
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

            fragmentTransaction.replace(R.id.readCenterTabView, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }

}