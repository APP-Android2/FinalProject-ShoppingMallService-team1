package kr.co.lion.finalproject_shoppingmallservice_team1.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
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