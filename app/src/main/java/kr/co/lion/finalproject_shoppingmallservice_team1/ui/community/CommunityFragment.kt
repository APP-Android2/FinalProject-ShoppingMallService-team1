package kr.co.lion.finalproject_shoppingmallservice_team1.ui.community

import CommunityPost
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.COMMUNITY_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.CommunityDao
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.UserDao
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCommunityBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCommunityContentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.community.viewmodel.CommunityViewModel
@RequiresApi(Build.VERSION_CODES.O)
class CommunityFragment : Fragment() {
    lateinit var fragmentCommunityBinding: FragmentCommunityBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var drawerLayout : DrawerLayout
    val toolbarCommunityTitle = view?.findViewById<TextView>(R.id.toolbarCommunity_title)
    var isDrawerOpen = false
    private lateinit var communtyViewModel: CommunityViewModel
    lateinit var communiWriteActivityLauncher: ActivityResultLauncher<Intent>

    var communityPostList = mutableListOf<CommunityPost>()

    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentCommunityBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, container, false)
        communtyViewModel = CommunityViewModel()
        fragmentCommunityBinding.communityViewModel = communtyViewModel
        fragmentCommunityBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity


        settingToolbarCommunity()
        gettingListData()
        settingRecyclerCommnunityContent()
        settingDrawer()
        settingEvent()
        settingChip()

        return fragmentCommunityBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contract = ActivityResultContracts.StartActivityForResult()
        communiWriteActivityLauncher = registerForActivityResult(contract){
            if (it != null){
                when(it.resultCode){
                    Activity.RESULT_OK -> {
                        if (it.data != null){
                            val value = it?.data!!.getIntExtra("CommunityWrite", 0)
                            Log.d("test1234", "gettingListData")
                            gettingListData()
                        }
                    }
                }
            }
        }
    }

    // 툴바 설정
    fun settingToolbarCommunity(){
        fragmentCommunityBinding.apply {
            toolbarCommunity.apply {
                setNavigationIcon(R.drawable.menu)

                setNavigationOnClickListener {
                    isDrawerOpen = !isDrawerOpen
                    if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        drawerLayout.closeDrawer(Gravity.LEFT)
                    } else {
                        drawerLayout.openDrawer(Gravity.LEFT)
                    }

                }
                inflateMenu(R.menu.menu_community)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.search -> {
                            val intent = Intent(navigationActivity, CommunitySearchActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    true
                }
            }
        }
    }

    // 메뉴 설정
    fun settingDrawer(){
        fragmentCommunityBinding.apply {
            // 커뮤니티
            textViewDrawerCommunity.setOnClickListener {
                drawerLayout.closeDrawer(Gravity.LEFT)
            }
            // 내가 쓴 글
            textViewDrawerMyContent.setOnClickListener {
                replaceFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_MYCONTENT_FRAGMENT, true, true, null)
                drawerLayout.closeDrawer(Gravity.LEFT)
            }
            // 댓글 단 글
            textViewDrawerCommentContent.setOnClickListener {
                replaceFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_COMMENTCONTENT_FRAGMENT, true, true, null)
                drawerLayout.closeDrawer(Gravity.LEFT)
            }
            // 추천한 글
            textViewDrawerLikeContent.setOnClickListener {
                replaceFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_LIKECONTENT_FRAGMENT, true, true, null)
                drawerLayout.closeDrawer(Gravity.LEFT)
            }
        }
    }

    // 글쓰기 버튼
    fun settingEvent(){
        fragmentCommunityBinding.apply {
            buttonWrite.apply {
                setOnClickListener {
                    val intent = Intent(navigationActivity, CommunityWriteActivity::class.java)
                    communiWriteActivityLauncher.launch(intent)
                }
            }
        }
    }

    // 게시글 목록
    fun settingRecyclerCommnunityContent(){
        fragmentCommunityBinding.apply {
            recyclerViewContent.apply {
                adapter = CommnunityContentRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(navigationActivity)

                val deco = MaterialDividerItemDecoration(navigationActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    private fun settingChip(){
        fragmentCommunityBinding.chipCommunityEntire.apply {
            setOnClickListener {
                val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)

                val popup = PopupMenu(contextWrapper, this)
                popup.inflate(R.menu.menu_community_chip)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        // 거리순
                        R.id.menuItemCommunityDistance -> {
                            text = "거리순"
                        }
                        // 최신순
                        R.id.menuItemCommunityRecent -> {
                            text = "최신순"
                        }
                    }
                    true
                }

                popup.show()
            }
        }
    }

    inner class CommnunityContentRecyclerViewAdapter:RecyclerView.Adapter<CommnunityContentRecyclerViewAdapter.CommunityContentViewHolder>(){
        inner class CommunityContentViewHolder(rowCommunityBinding: RowCommunityContentBinding):RecyclerView.ViewHolder(rowCommunityBinding.root){
            val rowCommunityBinding:RowCommunityContentBinding

            init {
                this.rowCommunityBinding = rowCommunityBinding

                rowCommunityBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityContentViewHolder {
            val rowCommunityBinding = RowCommunityContentBinding.inflate(layoutInflater)
            val communityContentViewHolder = CommunityContentViewHolder(rowCommunityBinding)
            return communityContentViewHolder
        }

        override fun getItemCount(): Int {
            return communityPostList.size
        }

        override fun onBindViewHolder(holder: CommunityContentViewHolder, position: Int) {
            CoroutineScope(Dispatchers.Main).launch{
                val user = UserDao.getUser(communityPostList[position].userId)

                holder.rowCommunityBinding.textViewCommmunityTitle.text = communityPostList[position].title
                holder.rowCommunityBinding.textViewCommunityTag.text = "해시태그"
                holder.rowCommunityBinding.textViewCommnunityContent.text = communityPostList[position].content
                holder.rowCommunityBinding.textViewCommunityNickname.text = user?.nickName
                holder.rowCommunityBinding.textViewCommunityAddress.text = "서울 서초구"
                holder.rowCommunityBinding.textViewCommunityTime.text = communityPostList[position].postTime

                holder.rowCommunityBinding.textViewCommunityLike.text = "1"
                holder.rowCommunityBinding.textViewCommunityComment.text = "2"
                holder.rowCommunityBinding.textViewCommunityView.text = "3"

                holder.rowCommunityBinding.root.setOnClickListener {
                    val intent = Intent(navigationActivity, ContentActivity::class.java)
                    intent.putExtra("communityContent", "${communityPostList[position].communityPostId}")
                    startActivity(intent)
                }
            }
        }
    }

    fun gettingListData(){
        CoroutineScope(Dispatchers.Main).launch {
            communityPostList = CommunityDao.getCommunityList()

            fragmentCommunityBinding.recyclerViewContent.adapter?.notifyDataSetChanged()
        }
    }

    fun replaceFragment(name: COMMUNITY_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        val fragmentTransaction = childFragmentManager.beginTransaction()

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){

            COMMUNITY_FRAGMENT_NAME.COMMUNITY_MYCONTENT_FRAGMENT -> {
                newFragment = CommunityMyContentFragment()
            }

            COMMUNITY_FRAGMENT_NAME.COMMUNITY_COMMENTCONTENT_FRAGMENT -> {
                newFragment = CommunityCommentContentFragment()
            }

            COMMUNITY_FRAGMENT_NAME.COMMUNITY_LIKECONTENT_FRAGMENT -> {
                newFragment = CommunityLikeContentFragment()
            }
        }

        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){

            // 애니메이션 설정
            if(isAnimate == true){
                if(oldFragment != null){
                    // old에서 new가 새롭게 보여질 때 old의 애니메이션
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    // new에서 old로 되돌아갈때 old의 애니메이션
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }

                // old에서 new가 새롭게 보여질 때 new의 애니메이션
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                // new에서 old로 되돌아갈때 new의 애니메이션
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }

            // Fragment를 교체한다.(이전 Fragment가 없으면 새롭게 추가하는 역할을 수행한다)
            // 첫 번째 매개 변수 : Fragment를 배치할 FragmentContainerView의 ID
            // 두 번째 매개 변수 : 보여주고하는 Fragment 객체를
            fragmentTransaction.replace(R.id.fragmentCommunity, newFragment!!)

            // addToBackStack 변수의 값이 true면 새롭게 보여질 Fragment를 BackStack에 포함시켜 준다.
            if(addToBackStack == true){
                // BackStack 포함 시킬때 이름을 지정해주면 원하는 Fragment를 BackStack에서 제거할 수 있다.
                fragmentTransaction.addToBackStack(name.str)
            }
            // Fragment 교체를 확정한다.
            fragmentTransaction.commit()
        }
    }
    // BackStack에서 Fragment를 제거한다.
    fun removeFragment(name: COMMUNITY_FRAGMENT_NAME){
        SystemClock.sleep(200)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        childFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}