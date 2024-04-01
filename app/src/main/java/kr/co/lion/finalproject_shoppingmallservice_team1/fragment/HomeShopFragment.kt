package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_BOTTOM_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_SHOP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.Navigation_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeShopBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel.HomeShopViewModel

class HomeShopFragment : Fragment() {
    lateinit var fragmentHomeShopBinding: FragmentHomeShopBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var homeShopViewModel: HomeShopViewModel

    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeShopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_shop, container, false)
        homeShopViewModel = HomeShopViewModel()
        fragmentHomeShopBinding.homeShopViewModel = homeShopViewModel
        fragmentHomeShopBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingEvent()

        return fragmentHomeShopBinding.root
    }

    fun settingToolbar() {
        fragmentHomeShopBinding.apply {
            toolbarHomeShop.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    SystemClock.sleep(200)
                    parentFragmentManager.popBackStack()
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    fun settingEvent(){
        fragmentHomeShopBinding.apply {
            buttonHomeShopContain.apply {
                setOnClickListener {
                    replaceFragment(HOME_SHOP_FRAGMENT_NAME.SHOP_CONTAIN_FRAGMENT, true, false, null)
                }
            }

            buttonHomeShopSwap.apply {
                setOnClickListener {
                    SystemClock.sleep(200)
                    parentFragmentManager.popBackStack()
                    navigationActivity.replaceFragment(Navigation_FRAGMENT_NAME.CENTER_FRAGMENT, true, true, null)
                }
            }
        }
    }

    fun replaceFragment(name: HOME_SHOP_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        val fragmentTransaction = childFragmentManager.beginTransaction().setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            HOME_SHOP_FRAGMENT_NAME.SHOP_CONTAIN_FRAGMENT -> {
                newFragment = HomeShopContainFragment()
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

            fragmentTransaction.replace(R.id.frameHomeShop, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }
}