package kr.co.lion.finalproject_shoppingmallservice_team1.ui.community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.Tools
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityCommunitySearchBinding

class CommunitySearchActivity : AppCompatActivity() {
    lateinit var activityCommunitySearchBinding: ActivityCommunitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCommunitySearchBinding = ActivityCommunitySearchBinding.inflate(layoutInflater)
        setContentView(activityCommunitySearchBinding.root)

        settingToolbar()
    }
    fun settingToolbar() {
        activityCommunitySearchBinding.apply {
            toolbarCommunitySearch.apply {
                // 검색창 클릭 후 화면이므로 키보드가 올라가고 검색창의 커서 활성화
                Tools.showSoftInput(this@CommunitySearchActivity, searchViewCommunitySearch)

                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    // 뒤로 가면서 키보드 숨김
                    Tools.hideSoftInput(this@CommunitySearchActivity)
                    finish()
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }
}