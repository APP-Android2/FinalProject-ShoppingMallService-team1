package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyReviewTab2Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyReviewTab2Binding

class MyReviewTab2Fragment : Fragment() {

    lateinit var fragmentMyReviewTab2Binding: FragmentMyReviewTab2Binding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyReviewTab2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_review_tab2, container, false)
        navigationActivity = activity as NavigationActivity

        settingRecyclerViewMyReviewTab2()

        return fragmentMyReviewTab2Binding.root
    }

    // RecyclerView 구성
    fun settingRecyclerViewMyReviewTab2(){
        fragmentMyReviewTab2Binding.apply {
            recyclerViewMyReviewTab2.apply {
                // 어댑터
                adapter = MyReviewTab2RecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class MyReviewTab2RecyclerViewAdapter : RecyclerView.Adapter<MyReviewTab2RecyclerViewAdapter.MyReviewTab2ViewHolder>() {
        // ViewHolder
        inner class MyReviewTab2ViewHolder(rowMyReviewTab2Binding: RowMyReviewTab2Binding) : RecyclerView.ViewHolder(rowMyReviewTab2Binding.root){
            val rowMyReviewTab2Binding: RowMyReviewTab2Binding

            init {
                this.rowMyReviewTab2Binding = rowMyReviewTab2Binding

                this.rowMyReviewTab2Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewTab2ViewHolder {
            val rowMyReviewTab2Binding = RowMyReviewTab2Binding.inflate(layoutInflater)
            val myReviewTab2ViewHolder = MyReviewTab2ViewHolder(rowMyReviewTab2Binding)

            return myReviewTab2ViewHolder
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: MyReviewTab2ViewHolder, position: Int) {
            holder.rowMyReviewTab2Binding.tvRowMyReviewTab2Name.text = "고길동"
            holder.rowMyReviewTab2Binding.tvRowMyReviewTab2Date.text = "2000-04-04"
            holder.rowMyReviewTab2Binding.tvRowMyReviewTrainerName.text = "트레이너명 $position"
            holder.rowMyReviewTab2Binding.tvRowMyReviewTab2Contents.text = "우리쌤 체고 $position"
        }
    }
}