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
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCommunityMyContentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMycontentBinding

class CommunityMyContentFragment : Fragment() {
    lateinit var fragmentCommunityMyContentBinding: FragmentCommunityMyContentBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var communityFragment: CommunityFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCommunityMyContentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community_my_content, container, false)
        communityFragment = parentFragment as CommunityFragment
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        settingRecyclerMyContent()

        return fragmentCommunityMyContentBinding.root
    }

    fun settingToolbar() {
        fragmentCommunityMyContentBinding.apply {
            toolbarCommunityMyContent.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_MYCONTENT_FRAGMENT)
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    fun settingRecyclerMyContent(){
        fragmentCommunityMyContentBinding.apply {
            recyclerViewCommunityMyContent.apply {
                adapter = MyContentRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(navigationActivity)

                val deco = MaterialDividerItemDecoration(navigationActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    inner class MyContentRecyclerViewAdapter:RecyclerView.Adapter<MyContentRecyclerViewAdapter.MyContentViewHolder>(){
        inner class MyContentViewHolder(rowMycontentBinding: RowMycontentBinding):RecyclerView.ViewHolder(rowMycontentBinding.root){
            val rowMycontentBinding:RowMycontentBinding

            init {
                this.rowMycontentBinding = rowMycontentBinding

                rowMycontentBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContentViewHolder {
            val rowMycontentBinding = RowMycontentBinding.inflate(layoutInflater)
            val myContentViewHolder = MyContentViewHolder(rowMycontentBinding)

            return myContentViewHolder
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: MyContentViewHolder, position: Int) {
            holder.rowMycontentBinding.textViewCommmunityTitle.text = "글 제목"
            holder.rowMycontentBinding.textViewCommunityTag.text = "해시태그"
            holder.rowMycontentBinding.textViewCommnunityContent.text = "글 내용--------------------"
            holder.rowMycontentBinding.textViewCommunityAddress.text = "서울 서초구"
            holder.rowMycontentBinding.textViewCommunityTime.text = "3시간 전"

            holder.rowMycontentBinding.textViewCommunityLike.text = "1"
            holder.rowMycontentBinding.textViewCommunityComment.text = "2"
            holder.rowMycontentBinding.textViewCommunityView.text = "3"

            holder.rowMycontentBinding.root.setOnClickListener {
                val intent = Intent(navigationActivity, ContentActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communityFragment.removeFragment(COMMUNITY_FRAGMENT_NAME.COMMUNITY_MYCONTENT_FRAGMENT)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}