package kr.co.lion.finalproject_shoppingmallservice_team1.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.lion.finalproject_shoppingmallservice_team1.FirebaseAuthHelper
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.login.LoginActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash 화면
        installSplashScreen()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        FirebaseAuthHelper.initializeFirebaseAuth()  // Firebase 인증 초기화
        checkLoggedInUser() // 로그인된 사용자 확인

        settingOnBoarding()
        startLoginActivity()
    }

    fun permissionCheck(){
        // 확인할 권한 목록
        val permissionList = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_MEDIA_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

        // 권한 설정
        requestPermissions(permissionList, 0)
    }

    private fun settingOnBoarding(){
        activityMainBinding.mainViewPager2.adapter = MainViewPageAdapter(this@MainActivity)
        activityMainBinding.mainCircleIndicator.setViewPager(activityMainBinding.mainViewPager2)
    }

    private fun startLoginActivity(){

        activityMainBinding.mainButton.setOnClickListener {

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)

            this@MainActivity.finish()
        }
    }

    // 로그인된 사용자 확인
    private fun checkLoggedInUser(){

        val currentUser = FirebaseAuthHelper.getCurrentUser()
        if (currentUser != null) {

            // 사용자가 이미 로그인되어 있으면 NavigationActivity로 이동
            startActivity(Intent(this@MainActivity, NavigationActivity::class.java))
            finish()
        }
    }

    inner class MainViewPageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> OnBoarding1Fragment()
                1 -> OnBoarding2Fragment()
                2 -> OnBoarding3Fragment()
                else -> throw IllegalArgumentException("Invalid position $position")
            }
        }
    }
}