package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyReviewTab1Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyReviewTab1Binding

class MyReviewTab1Fragment : Fragment() {

    lateinit var fragmentMyReviewTab1Binding: FragmentMyReviewTab1Binding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyReviewTab1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_review_tab1, container, false)
        navigationActivity = activity as NavigationActivity

        settingRecyclerViewMyReviewTab1()

        return fragmentMyReviewTab1Binding.root
    }

    // RecyclerView 설정
    fun settingRecyclerViewMyReviewTab1(){
        fragmentMyReviewTab1Binding.apply {
            recyclerViewMyReviewTab1.apply {
                // 어댑터
                adapter = MyReviewTab1RecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class MyReviewTab1RecyclerViewAdapter : RecyclerView.Adapter<MyReviewTab1RecyclerViewAdapter.MyReviewTab1ViewHolder>() {
        // ViewHolder
        inner class MyReviewTab1ViewHolder(rowMyReviewTab1Binding: RowMyReviewTab1Binding) : RecyclerView.ViewHolder(rowMyReviewTab1Binding.root){
            val rowMyReviewTab1Binding: RowMyReviewTab1Binding

            init {
                this.rowMyReviewTab1Binding = rowMyReviewTab1Binding

                this.rowMyReviewTab1Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewTab1ViewHolder {
            val rowMyReviewTab1Binding = RowMyReviewTab1Binding.inflate(layoutInflater)
            val myReviewTab1ViewHolder = MyReviewTab1ViewHolder(rowMyReviewTab1Binding)

            return myReviewTab1ViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: MyReviewTab1ViewHolder, position: Int) {
            holder.rowMyReviewTab1Binding.tvRowMyReviewTab1Name.text = "홍길동"
            holder.rowMyReviewTab1Binding.tvRowMyReviewTab1Date.text = "2024-04-04"
            holder.rowMyReviewTab1Binding.tvRowMyReviewProductName.text = "제품명 $position"
            holder.rowMyReviewTab1Binding.tvRowMyReviewTab1Contents.text = "우리 체육관 조아요 $position"
        }
    }
}