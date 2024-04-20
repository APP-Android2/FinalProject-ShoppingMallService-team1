package kr.co.lion.finalproject_shoppingmallservice_team1.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.firebase.auth.FirebaseAuth
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityMainBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    // 확인할 권한 목록
    val permissionList = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash 화면
        installSplashScreen()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 권한 확인
        requestPermissions(permissionList, 0)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        if(user != null){
            startActivity(Intent(this@MainActivity, NavigationActivity::class.java))
            finish()
        }

        settingOnBoarding()
        startLoginActivity()
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