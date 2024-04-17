package kr.co.lion.finalproject_shoppingmallservice_team1.ui.community

import android.content.Intent
import android.os.Bundle
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
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCommunityCommentContentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCommentcontentBinding

class CommunityCommentContentFragment : Fragment() {
    lateinit var fragmentCommunityCommentContentBinding: FragmentCommunityCommentContentBinding
    lateinit var communityFragment: CommunityFragment
    lateinit var navigationActivity: NavigationActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCommunityCommentContentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community_comment_content, container, false)
        communityFragment = parentFragment as CommunityFragment
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        settingRecyclerCommentContent()

        return fragmentCommunityCommentContentBinding.root
    }

    fun settingToolbar() {
        fragmentCommunityCommentContentBinding.apply {
            toolbarCommunityCommentContent.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_COMMENTCONTENT_FRAGMENT)
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    fun settingRecyclerCommentContent(){
        fragmentCommunityCommentContentBinding.apply {
            recyclerViewCommunityCommentContent.apply {
                adapter = CommentContentRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(navigationActivity)

                val deco = MaterialDividerItemDecoration(navigationActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    inner class CommentContentRecyclerViewAdapter: RecyclerView.Adapter<CommentContentRecyclerViewAdapter.CommentContentViewHolder>(){
        inner class CommentContentViewHolder(rowCommentcontentBinding: RowCommentcontentBinding): RecyclerView.ViewHolder(rowCommentcontentBinding.root){
            val rowCommentcontentBinding:RowCommentcontentBinding

            init {
                this.rowCommentcontentBinding = rowCommentcontentBinding

                rowCommentcontentBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentContentViewHolder {
            val rowCommentcontentBinding = RowCommentcontentBinding.inflate(layoutInflater)
            val commentContentViewHolder =CommentContentViewHolder(rowCommentcontentBinding)

            return commentContentViewHolder
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: CommentContentViewHolder, position: Int) {
            holder.rowCommentcontentBinding.textViewComment.text = "내가 단 댓글"
            holder.rowCommentcontentBinding.textViewCommentDate.text = "3시간 전"

            holder.rowCommentcontentBinding.root.setOnClickListener {
                val intent = Intent(navigationActivity, ContentActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_COMMENTCONTENT_FRAGMENT)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}