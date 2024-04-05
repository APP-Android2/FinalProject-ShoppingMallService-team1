package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyPickTab2Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyPickTab2Binding

class MyPickTab2Fragment : Fragment() {

    lateinit var fragmentMyPickTab2Binding: FragmentMyPickTab2Binding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyPickTab2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_pick_tab2, container, false)
        navigationActivity = activity as NavigationActivity

        settingRecyclerViewMyPickTab2()

        return fragmentMyPickTab2Binding.root
    }

    // RecyclerView 설정
    fun settingRecyclerViewMyPickTab2(){
        fragmentMyPickTab2Binding.apply {
            recyclerViewMyPickTab2.apply {
                // 어댑터
                adapter = MyPickTab2RecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = GridLayoutManager(navigationActivity, 2)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class MyPickTab2RecyclerViewAdapter : RecyclerView.Adapter<MyPickTab2RecyclerViewAdapter.MyPickTab2ViewHolder>() {
        // ViewHolder
        inner class MyPickTab2ViewHolder(rowMyPickTab2Binding: RowMyPickTab2Binding) : RecyclerView.ViewHolder(rowMyPickTab2Binding.root){
            val rowMyPickTab2Binding: RowMyPickTab2Binding

            init {
                this.rowMyPickTab2Binding = rowMyPickTab2Binding

                this.rowMyPickTab2Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPickTab2ViewHolder {
            val rowMyPickTab2Binding = RowMyPickTab2Binding.inflate(layoutInflater)
            val myPickTab2ViewHolder = MyPickTab2ViewHolder(rowMyPickTab2Binding)

            return myPickTab2ViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: MyPickTab2ViewHolder, position: Int) {
            holder.rowMyPickTab2Binding.tvRowMyPickCenterName.text = "시설명 $position"
        }
    }
}