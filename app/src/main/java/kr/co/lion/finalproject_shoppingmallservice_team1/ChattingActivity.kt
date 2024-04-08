package kr.co.lion.finalproject_shoppingmallservice_team1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityChattingBinding

class ChattingActivity : AppCompatActivity() {
    lateinit var activityChattingBinding: ActivityChattingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityChattingBinding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(activityChattingBinding.root)

        settingToolbar()
    }
    fun settingToolbar() {
        activityChattingBinding.apply {
            toolbarChatting.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    finish()
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }
}