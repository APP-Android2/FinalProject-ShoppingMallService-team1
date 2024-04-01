package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeChatBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowHomeChatBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel.HomeChatViewModel

class HomeChatFragment : Fragment() {
    lateinit var fragmentHomeChatBinding: FragmentHomeChatBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var homeChatViewModel: HomeChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_chat, container, false)
        homeChatViewModel = HomeChatViewModel()
        fragmentHomeChatBinding.homeChatViewModel = homeChatViewModel
        fragmentHomeChatBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingRecyclerViewHomeChat()

        return fragmentHomeChatBinding.root
    }

    fun settingToolbar() {
        fragmentHomeChatBinding.apply {
            toolbarHomeChat.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    SystemClock.sleep(200)
                    parentFragmentManager.popBackStack()
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    fun settingRecyclerViewHomeChat(){
        fragmentHomeChatBinding.apply {
            recyclerViewHomeChat.apply {
                adapter = HomeChatRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(navigationActivity)

                val deco = MaterialDividerItemDecoration(navigationActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    inner class HomeChatRecyclerViewAdapter:RecyclerView.Adapter<HomeChatRecyclerViewAdapter.HomeChatViewHolder>(){
        inner class HomeChatViewHolder(rowHomeChatBinding: RowHomeChatBinding):RecyclerView.ViewHolder(rowHomeChatBinding.root){
            val rowHomeChatBinding:RowHomeChatBinding

            init {
                this.rowHomeChatBinding = rowHomeChatBinding
                rowHomeChatBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChatViewHolder {
            val rowHomeChatBinding = RowHomeChatBinding.inflate(layoutInflater)
            val homeChatViewHolder = HomeChatViewHolder(rowHomeChatBinding)
            return homeChatViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: HomeChatViewHolder, position: Int) {
            holder.rowHomeChatBinding.textViewHomeChatName.text = "홀길동"
            holder.rowHomeChatBinding.textViewHomeChatContent.text = "헬스장 가격 문의 답장입니다."
            holder.rowHomeChatBinding.textViewHomeChatDate.text = "2024-03-29"
        }
    }
}