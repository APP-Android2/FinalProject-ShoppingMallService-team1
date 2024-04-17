package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
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
        handleBackPress()
        settingRecyclerViewMyMembership()

        return fragmentMyMembershipBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MyMembershipFragment 가 실행될 때 하단바가 보이지 않도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()

        // MyMembershipFragment 가 제거될 때 하단바가 보이도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = true
    }


    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyMembershipBinding.apply {
            toolbarMyMembership.apply {
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    backProcess()
                }
                // 메뉴
                inflateMenu(R.menu.menu_my_membership)
            }
        }
    }

    // 뒤로가기 처리
    private fun backProcess(){
        SystemClock.sleep(200)
        parentFragmentManager.popBackStack()
    }

    // 뒤로가기 처리(단말기)
    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로가기
                backProcess()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
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