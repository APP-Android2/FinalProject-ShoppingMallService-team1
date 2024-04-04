package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.finalproject_shoppingmallservice_team1.COMMUNITY_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_SHOP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCommunityLikeContentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowLikecontentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMycontentBinding

class CommunityLikeContentFragment : Fragment() {
    lateinit var fragmentCommunityLikeContentBinding: FragmentCommunityLikeContentBinding
    lateinit var navigationActivity:NavigationActivity
    lateinit var communityFragment: CommunityFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCommunityLikeContentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community_like_content, container, false)
        communityFragment = parentFragment as CommunityFragment
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        settingRecyclerLikeContent()

        return fragmentCommunityLikeContentBinding.root
    }
    fun settingToolbar() {
        fragmentCommunityLikeContentBinding.apply {
            toolbarCommunityLike.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_LIKECONTENT_FRAGMENT)
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    fun settingRecyclerLikeContent(){
        fragmentCommunityLikeContentBinding.apply {
            recyclerViewCommunityLike.apply {
                adapter = LikeContentRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(navigationActivity)

                val deco = MaterialDividerItemDecoration(navigationActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    inner class LikeContentRecyclerViewAdapter: RecyclerView.Adapter<LikeContentRecyclerViewAdapter.LikeContentViewHolder>(){
        inner class LikeContentViewHolder(rowLikecontentBinding: RowLikecontentBinding): RecyclerView.ViewHolder(rowLikecontentBinding.root){
            val rowLikecontentBinding: RowLikecontentBinding

            init {
                this.rowLikecontentBinding = rowLikecontentBinding
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeContentViewHolder {
            val rowLikecontentBinding = RowLikecontentBinding.inflate(layoutInflater)
            val likeContentViewHolder = LikeContentViewHolder(rowLikecontentBinding)

            return likeContentViewHolder
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: LikeContentViewHolder, position: Int) {
            holder.rowLikecontentBinding.textViewCommmunityTitle.text = "글 제목"
            holder.rowLikecontentBinding.textViewCommunityTag.text = "해시태그"
            holder.rowLikecontentBinding.textViewCommnunityContent.text = "글 내용--------------------"
            holder.rowLikecontentBinding.textViewCommunityNickname.text = "닉네임"
            holder.rowLikecontentBinding.textViewCommunityAddress.text = "서울 서초구"
            holder.rowLikecontentBinding.textViewCommunityTime.text = "3시간 전"

            holder.rowLikecontentBinding.textViewCommunityLike.text = "1"
            holder.rowLikecontentBinding.textViewCommunityComment.text = "2"
            holder.rowLikecontentBinding.textViewCommunityView.text = "3"
        }
    }

    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_LIKECONTENT_FRAGMENT)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}