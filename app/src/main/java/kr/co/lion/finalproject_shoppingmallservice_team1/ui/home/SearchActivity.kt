package kr.co.lion.finalproject_shoppingmallservice_team1.ui.home

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.Tools
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.SearchDao
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivitySearchBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowSearchPopularBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowSearchRecentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.model.Search

class SearchActivity : AppCompatActivity() {
    lateinit var activitySearchBinding: ActivitySearchBinding
    var recentSearchList = mutableListOf<Search>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(activitySearchBinding.root)


        settingToolbar()
        settingTabLayout()
        gettingListData()
        settingRecentSearch()
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

    fun settingRecentSearch(){
        activitySearchBinding.apply {
            searchViewSearch.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                // 검색 버튼 눌렀을 때
                override fun onQueryTextSubmit(queary: String?): Boolean {
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.d("test1234", "Search: ${queary}")
                        if (queary != null) {
                            val sequence = SearchDao.getSequence()
                            SearchDao.updateSequence(sequence + 1)

                            val idx = sequence + 1
                            val searchData = queary

                            val search = Search(idx, searchData)
                            Log.d("test1234", "Search: ${search}")
                            SearchDao.insertRecentSearch(search)
                            Log.d("test1234", "Search: ${SearchDao.getSearchList()}")

                            gettingListData()

                        }
                    }
                    return true
                }

                // 검색창에서 글자 변경이 일어날 때
                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
            })
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
            return recentSearchList.size
        }

        override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
            holder.rowSearchRecentBinding.textViewRowRecentSearch.text = recentSearchList[position].searchData
        }
    }

    fun gettingListData(){
        CoroutineScope(Dispatchers.Main).launch {
            recentSearchList = SearchDao.getSearchList()

            activitySearchBinding.recyclerVIewSearch.adapter?.notifyDataSetChanged()
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