package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyMembershipBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyMembershipBinding

class MyMembershipFragment : Fragment() {

    lateinit var fragmentMyMembershipBinding: FragmentMyMembershipBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyMembershipBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_membership, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingRecyclerViewMyMembership()

        return fragmentMyMembershipBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyMembershipBinding.apply {
            toolbarMyMembership.apply {
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    navigationActivity.removeFragment(NAVIGATION_FRAGMENT_NAME.MY_MEMBERSHIP_FRAGMENT)
                }
                // 메뉴
                inflateMenu(R.menu.menu_my_membership)
            }
        }
    }

    // RecyclerView 설정
    fun settingRecyclerViewMyMembership(){
        fragmentMyMembershipBinding.apply {
            recyclerViewMyMembership.apply {
                // 어댑터
                adapter = MyMembershipRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class MyMembershipRecyclerViewAdapter : RecyclerView.Adapter<MyMembershipRecyclerViewAdapter.MyMembershipViewHolder>() {
        // ViewHolder
        inner class MyMembershipViewHolder(rowMyMembershipBinding: RowMyMembershipBinding) : RecyclerView.ViewHolder(rowMyMembershipBinding.root){
            val rowMyMembershipBinding: RowMyMembershipBinding

            init {
                this.rowMyMembershipBinding = rowMyMembershipBinding

                this.rowMyMembershipBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMembershipViewHolder {
            val rowMyMembershipBinding = RowMyMembershipBinding.inflate(layoutInflater)
            val myMembershipViewHolder = MyMembershipViewHolder(rowMyMembershipBinding)

            return myMembershipViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: MyMembershipViewHolder, position: Int) {
            holder.rowMyMembershipBinding.tvRowMyCenterName.text = "상호명 $position"
            holder.rowMyMembershipBinding.tvRowMyCourse.text = "과정 이름 $position"
            holder.rowMyMembershipBinding.tvRowMyRemainDays.text = "남은 기간 ${position}일"
        }
    }
}