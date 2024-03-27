package kr.co.lion.finalproject_shoppingmallservice_team1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMaingBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMaingBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMaingBinding.root)
    }
}