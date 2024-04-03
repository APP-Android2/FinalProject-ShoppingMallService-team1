package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_SHOP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityShoppingCartBinding

class ShoppingCartActivity : AppCompatActivity() {

    lateinit var activityShoppingcartBinding: ActivityShoppingCartBinding
    lateinit var navigationActivity: NavigationActivity

    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShoppingcartBinding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(activityShoppingcartBinding.root)



        settingToolbar()
        settingEvent()
    }
    fun settingToolbar() {
        activityShoppingcartBinding.apply {
            toolbarHomeShop.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    // 바로 finish()해야 애니메이션이 제대로 구현됨(안하면 반대로 됨)
//                    val Backintent = Intent(this@ShoppingCartActivity,  NavigationActivity::class.java)
//                    startActivity(Backintent)
                    finish()
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    fun settingEvent(){
        activityShoppingcartBinding.apply {
            // 제품을 담았을 때 장바구니 화면이 나옴
            buttonHomeShopContain.apply {
                setOnClickListener {
                    replaceFragment(HOME_SHOP_FRAGMENT_NAME.SHOP_CONTAIN_FRAGMENT, true, false, null)
                }
            }

            // 다양한 운동시설 보러가기 버튼
            buttonHomeShopSwap.apply {
                setOnClickListener {
                    val resultIntent = Intent()
                    resultIntent.putExtra("buttonHomeShopSwap", 1)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }

    fun replaceFragment(name: HOME_SHOP_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        // 액티비티와 프래그먼트 사이의 전환이므로 supportFragmentManager이용
        val fragmentTransaction = supportFragmentManager.beginTransaction().setReorderingAllowed(true)

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

    fun removeFragment(name:HOME_SHOP_FRAGMENT_NAME){
        SystemClock.sleep(200)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}