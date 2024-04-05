package kr.co.lion.finalproject_shoppingmallservice_team1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityCommunityEditBinding

class CommunityEditActivity : AppCompatActivity() {
    lateinit var activityCommunityEditBinding: ActivityCommunityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCommunityEditBinding = ActivityCommunityEditBinding.inflate(layoutInflater)
        setContentView(activityCommunityEditBinding.root)

        settingToolbar()
    }
    fun settingToolbar() {
        activityCommunityEditBinding .apply {
            toolbarCommunityEdit.apply {
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