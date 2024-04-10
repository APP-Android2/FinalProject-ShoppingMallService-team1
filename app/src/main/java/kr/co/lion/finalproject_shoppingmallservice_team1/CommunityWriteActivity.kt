package kr.co.lion.finalproject_shoppingmallservice_team1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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