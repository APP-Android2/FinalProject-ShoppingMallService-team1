package kr.co.lion.finalproject_shoppingmallservice_team1.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityNavigationBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.center.CenterFragment
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.center.ReadCenterActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.community.CommunityFragment
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.my.MyFragment
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.ReadTrainerActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.TrainerFragment
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.TrainerPostAddFragment

class NavigationActivity : AppCompatActivity() {

    lateinit var activityNavigationBinding: ActivityNavigationBinding

    // 프래그먼트의 주소값을 담을 프로퍼티
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    var currentFragmentId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityNavigationBinding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(activityNavigationBinding.root)

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            replaceFragment(NAVIGATION_FRAGMENT_NAME.HOME_FRAGMENT, false, true, null)
        }

        setBottomNavigationView()
    }
    // 하단 네비게이션뷰 설정
    fun setBottomNavigationView() {

        activityNavigationBinding.bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.fragment_home -> {
                    replaceFragment(NAVIGATION_FRAGMENT_NAME.HOME_FRAGMENT, false, true, null)
                    true
                }
                R.id.fragment_center -> {
                    replaceFragment(NAVIGATION_FRAGMENT_NAME.CENTER_FRAGMENT, false, true, null)
                    true
                }
                R.id.fragment_trainer -> {
                    replaceFragment(NAVIGATION_FRAGMENT_NAME.TRAINER_FRAGMENT, false, true, null)
                    true
                }
                R.id.fragment_comunity -> {
                    replaceFragment(NAVIGATION_FRAGMENT_NAME.COMMUNITY_FRAGMENT, false, true, null)
                    true
                }
                R.id.fragment_my -> {
                    replaceFragment(NAVIGATION_FRAGMENT_NAME.MY_FRAGMENT, false, true, null)
                    true
                }
                else -> false
            }
        }
    }

    // ReadTrainerActivity 실행
    fun readTrainerRequest(){
        val readTrainerIntent = Intent(this, ReadTrainerActivity::class.java)
        startActivity(readTrainerIntent)
    }

    // ReadCenterActivity 실행
    fun readCenterRequest(){
        val readCenterIntent = Intent(this, ReadCenterActivity::class.java)
        startActivity(readCenterIntent)
    }

    fun replaceFragment(name: NAVIGATION_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        if(currentFragmentId == name.num){
            return
        }

        SystemClock.sleep(200)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if(newFragment != null){

            oldFragment = newFragment
        }

        when(name){

            NAVIGATION_FRAGMENT_NAME.HOME_FRAGMENT -> {
                newFragment = HomeFragment()
                currentFragmentId = NAVIGATION_FRAGMENT_NAME.HOME_FRAGMENT.num
            }

            NAVIGATION_FRAGMENT_NAME.CENTER_FRAGMENT -> {
                newFragment = CenterFragment()
                currentFragmentId = NAVIGATION_FRAGMENT_NAME.CENTER_FRAGMENT.num
            }

            NAVIGATION_FRAGMENT_NAME.TRAINER_FRAGMENT -> {
                newFragment = TrainerFragment()
                currentFragmentId = NAVIGATION_FRAGMENT_NAME.TRAINER_FRAGMENT.num
            }

            NAVIGATION_FRAGMENT_NAME.COMMUNITY_FRAGMENT -> {
                newFragment = CommunityFragment()
                currentFragmentId = NAVIGATION_FRAGMENT_NAME.COMMUNITY_FRAGMENT.num
            }

            NAVIGATION_FRAGMENT_NAME.MY_FRAGMENT -> {
                newFragment = MyFragment()
                currentFragmentId = NAVIGATION_FRAGMENT_NAME.MY_FRAGMENT.num
            }

            NAVIGATION_FRAGMENT_NAME.TRAINER_POST_FRAGMENT -> {
                newFragment = TrainerPostAddFragment()
                currentFragmentId = NAVIGATION_FRAGMENT_NAME.TRAINER_POST_FRAGMENT.num
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

            // Fragment를 교체한다.(이전 Fragment가 없으면 새롭게 추가하는 역할을 수행한다)
            // 첫 번째 매개 변수 : Fragment를 배치할 FragmentContainerView의 ID
            // 두 번째 매개 변수 : 보여주고하는 Fragment 객체를
            fragmentTransaction.replace(R.id.main_container, newFragment!!)

            // addToBackStack 변수의 값이 true면 새롭게 보여질 Fragment를 BackStack에 포함시켜 준다.
            if(addToBackStack == true){
                // BackStack 포함 시킬때 이름을 지정해주면 원하는 Fragment를 BackStack에서 제거할 수 있다.
                fragmentTransaction.addToBackStack(name.str)
            }
            // Fragment 교체를 확정한다.
            fragmentTransaction.commit()
        }
    }

    // BackStack에서 Fragment를 제거한다.
    fun removeFragment(name: NAVIGATION_FRAGMENT_NAME){
        SystemClock.sleep(200)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    // 하단 네비게이션뷰의 아이콘 색상 업데이트
    fun updateIconColors(selectedItemId: Int) {
        activityNavigationBinding.apply {
            val menu = bottomNavigationView.menu
            for (i in 0 until menu.size()) {
                val menuItem = menu.getItem(i)
                val icon = menuItem.icon

                // 선택된 아이템이면 활성화 색으로, 아니면 비활성화 색으로 설정
                if (menuItem.itemId == selectedItemId) {
                    icon?.setTint(ContextCompat.getColor(this@NavigationActivity, R.color.black))
                } else {
                    icon?.setTint(ContextCompat.getColor(this@NavigationActivity, R.color.Pup_Color))
                }
            }
        }
    }
}