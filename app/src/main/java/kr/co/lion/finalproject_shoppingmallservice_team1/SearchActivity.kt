package kr.co.lion.finalproject_shoppingmallservice_team1

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.tabs.TabLayout
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivitySearchBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowSearchPopularBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowSearchRecentBinding

class SearchActivity : AppCompatActivity() {
    lateinit var activitySearchBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(activitySearchBinding.root)


        settingToolbar()
        settingTabLayout()
        // 리사이클뷰 초기 설정을 해놔야 탭 누르기 전부터 보임
        settingRecyclerViewRecentSearch()
    }

    fun settingToolbar(){
        activitySearchBinding.apply {
            // 검색창 클릭 후 화면이므로 키보드가 올라가고 검색창의 커서 활성화
            Tools.showSoftInput(this@SearchActivity, searchViewSearch)

            toolbarSearch.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    finish()
                }
                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    fun settingTabLayout(){
        activitySearchBinding.apply {
            searchTab.apply {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 탭이 선택되었을 때 호출되는 메서드
                        val position = tab?.position

                        when(position){
                            0 -> {
                                // 최근 검색어
                                settingRecyclerViewRecentSearch()
                            }
                            1 -> {
                                // 인기 검색어
                                settingRecyclerViewRecentPopular()
                            }
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        // 선택이 해제된 탭의 경우 처리할 내용

                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        // 이미 선택된 탭이 다시 선택된 경우 처리할 내용
                    }
                })
            }
        }
    }

    // 최근 검색어 목록
    fun settingRecyclerViewRecentSearch(){
        activitySearchBinding.apply {
            recyclerVIewSearch.apply {
                adapter = RecentSearchRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@SearchActivity)
            }
        }
    }

    inner class RecentSearchRecyclerViewAdapter:RecyclerView.Adapter<RecentSearchRecyclerViewAdapter.RecentSearchViewHolder>(){
        inner class RecentSearchViewHolder(rowSearchRecentBinding: RowSearchRecentBinding):RecyclerView.ViewHolder(rowSearchRecentBinding.root){
            val rowSearchRecentBinding:RowSearchRecentBinding

            init {
                this.rowSearchRecentBinding = rowSearchRecentBinding

                this.rowSearchRecentBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
            val rowSearchRecentBinding = RowSearchRecentBinding.inflate(layoutInflater)
            val recentSearchViewHolder = RecentSearchViewHolder((rowSearchRecentBinding))
            return recentSearchViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
            holder.rowSearchRecentBinding.textViewRowRecentSearch.text = "서울 헬스장"
        }
    }

    // 인기 검색어 목록
    fun settingRecyclerViewRecentPopular(){
        activitySearchBinding.apply {
            recyclerVIewSearch.apply {
                adapter = PopularSearchRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@SearchActivity)
            }
        }
    }

    inner class PopularSearchRecyclerViewAdapter:RecyclerView.Adapter<PopularSearchRecyclerViewAdapter.PopularSearchViewHolder>(){
        inner class PopularSearchViewHolder(rowSearchPopularBinding: RowSearchPopularBinding):RecyclerView.ViewHolder(rowSearchPopularBinding.root){
            val rowSearchPopularBinding:RowSearchPopularBinding

            init {
                this.rowSearchPopularBinding = rowSearchPopularBinding

                this.rowSearchPopularBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularSearchViewHolder {
            val rowSearchPopularBinding = RowSearchPopularBinding.inflate(layoutInflater)
            val popularSearchViewHolder = PopularSearchViewHolder((rowSearchPopularBinding))
            return popularSearchViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: PopularSearchViewHolder, position: Int) {
            holder.rowSearchPopularBinding.textViewRowPopularSearch.text = "인기있는 헬스장"
        }
    }
}