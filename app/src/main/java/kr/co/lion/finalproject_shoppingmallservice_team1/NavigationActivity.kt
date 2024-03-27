package kr.co.lion.finalproject_shoppingmallservice_team1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {

    lateinit var activityNavigationBinding: ActivityNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityNavigationBinding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(activityNavigationBinding.root)
    }
}