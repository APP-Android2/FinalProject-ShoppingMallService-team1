package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var homeViewModel: HomeViewModel

    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel = HomeViewModel()
        fragmentHomeBinding.homeViewModel = homeViewModel
        fragmentHomeBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingAddress()

        return fragmentHomeBinding.root
    }

    fun settingToolbar(){
        fragmentHomeBinding.apply {
            toolbarHome.apply {
                setNavigationIcon(R.drawable.notifications)

                setNavigationOnClickListener {
                    replaceFragment(HOME_FRAGMENT_NAME.HOME_ALARM_FRAGMENT, true, true, null)
                }

                inflateMenu(R.menu.home_menu)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemHomeChat -> {
                            replaceFragment(HOME_FRAGMENT_NAME.HOME_CHAT_FRAGMENT, true, true, null)
                        }
                        R.id.menuItemHomeShopping -> {
                            replaceFragment(HOME_FRAGMENT_NAME.HOME_SHOP_FRAGMENT, true, true, null)
                        }
                    }
                    true
                }
            }
        }
    }

    fun settingAddress(){
        fragmentHomeBinding.apply {
            btnHomeNowLocation.apply {
                setOnClickListener {
                    showHomeAddressBottomSheet()
                }
            }
        }

    }
    fun showHomeAddressBottomSheet(){
        val homeAddressBottomFragment = HomeAddressBottomFragment()
        homeAddressBottomFragment.show(navigationActivity.supportFragmentManager, "HomeAddressBottomSheet")
    }

    fun replaceFragment(name: HOME_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){

            HOME_FRAGMENT_NAME.HOME_ALARM_FRAGMENT -> {
                newFragment = HomeAlarmFragment()
            }

            HOME_FRAGMENT_NAME.HOME_CHAT_FRAGMENT -> {
                newFragment = HomeChatFragment()
            }

            HOME_FRAGMENT_NAME.HOME_SHOP_FRAGMENT -> {
                newFragment = HomeShopFragment()
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

            fragmentTransaction.replace(R.id.fragmentHome, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }
}