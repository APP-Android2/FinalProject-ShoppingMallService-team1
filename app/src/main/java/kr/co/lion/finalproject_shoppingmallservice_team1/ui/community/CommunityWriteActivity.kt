package kr.co.lion.finalproject_shoppingmallservice_team1.ui.community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityCommunityWriteBinding

class CommunityWriteActivity : AppCompatActivity() {
    lateinit var activityCommunityWriteBinding: ActivityCommunityWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCommunityWriteBinding = ActivityCommunityWriteBinding.inflate(layoutInflater)
        setContentView(activityCommunityWriteBinding.root)

        settingToolbar()
    }
    fun settingToolbar() {
        activityCommunityWriteBinding .apply {
            toolbarCommunityWrite.apply {
                setNavigationIcon(R.drawable.close)

                setNavigationOnClickListener {
                    finish()
                }

                inflateMenu(R.menu.menu_done)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemDone -> {
                            finish()
                        }
                    }
                    true
                }
            }
        }
    }
}