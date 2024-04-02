package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.AlarmActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ChatActivity
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
    lateinit var shoppingCartActivityLauncher:ActivityResultLauncher<Intent>

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contract = ActivityResultContracts.StartActivityForResult()
        shoppingCartActivityLauncher = registerForActivityResult(contract){
            if(it != null){
                when(it.resultCode){
                    Activity.RESULT_OK -> {
                        if (it.data!= null){
                            val value = it?.data!!.getIntExtra("buttonHomeShopSwap", 0)

                            //fragmentHomeBinding.textViewHomeMembership.append("${value}")
                            navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.CENTER_FRAGMENT, false, true, null)

                        }
                    }
                }
            }
        }
    }

    fun settingToolbar(){
        fragmentHomeBinding.apply {
            toolbarHome.apply {
                setNavigationIcon(R.drawable.notifications)

                setNavigationOnClickListener {
                    val intent = Intent(navigationActivity, AlarmActivity::class.java)
                    startActivity(intent)
                }

                inflateMenu(R.menu.home_menu)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemHomeChat -> {
                            val intent = Intent(navigationActivity, ChatActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.menuItemHomeShopping -> {
                            val intent = Intent(navigationActivity, ShoppingCartActivity::class.java)
                            //intent.putExtra("home", 1)
                            shoppingCartActivityLauncher.launch(intent)
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
}