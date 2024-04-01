package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeShopBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel.HomeShopViewModel

class HomeShopFragment : Fragment() {
    lateinit var fragmentHomeShopBinding: FragmentHomeShopBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var homeShopViewModel: HomeShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeShopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_shop, container, false)
        homeShopViewModel = HomeShopViewModel()
        fragmentHomeShopBinding.homeShopViewModel = homeShopViewModel
        fragmentHomeShopBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity

        settingToolbar()

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
}