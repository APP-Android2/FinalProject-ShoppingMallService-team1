package kr.co.lion.finalproject_shoppingmallservice_team1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMaingBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash 화면
        installSplashScreen()
        SystemClock.sleep(700)

        activityMaingBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMaingBinding.root)

        // 테스트 코드
        activityMaingBinding.button.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}