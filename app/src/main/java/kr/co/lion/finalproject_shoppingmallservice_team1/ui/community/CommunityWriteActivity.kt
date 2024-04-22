package kr.co.lion.finalproject_shoppingmallservice_team1.ui.community

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityCommunityWriteBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.community.viewmodel.CommunityViewModel
@RequiresApi(Build.VERSION_CODES.O)
class CommunityWriteActivity : AppCompatActivity() {
    lateinit var activityCommunityWriteBinding: ActivityCommunityWriteBinding
    lateinit var communityViewModel: CommunityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCommunityWriteBinding = ActivityCommunityWriteBinding.inflate(layoutInflater)
        communityViewModel = ViewModelProvider(this).get(CommunityViewModel::class.java)
        activityCommunityWriteBinding.communityViewModel = communityViewModel
        activityCommunityWriteBinding.lifecycleOwner = this

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
                            Log.d("test1234", "CommunityWrite")

                            val resultIntent = Intent()
                            resultIntent.putExtra("CommunityWrite", 1)
                            setResult(RESULT_OK, resultIntent)
                            communityViewModel?.updateData()
                            finish()
                        }
                    }
                    true
                }
            }
        }
    }
}