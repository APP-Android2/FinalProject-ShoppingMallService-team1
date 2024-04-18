package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCenterTab2Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCenterTab2Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity

class CenterTab2Fragment : Fragment() {

    lateinit var fragmentCenterTab2Binding: FragmentCenterTab2Binding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCenterTab2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_center_tab2, container, false)
        navigationActivity = activity as NavigationActivity

        settingRecyclerViewCenterTab2()

        return fragmentCenterTab2Binding.root
    }

    // RecyclerView 설정
    fun settingRecyclerViewCenterTab2(){
        fragmentCenterTab2Binding.apply {
            recyclerViewCenterTab2.apply {
                // 어댑터
                adapter = CenterTab2RecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class CenterTab2RecyclerViewAdapter : RecyclerView.Adapter<CenterTab2RecyclerViewAdapter.CenterTab2ViewHolder>() {

        // ViewHolder
        inner class CenterTab2ViewHolder(rowCenterTab2Binding: RowCenterTab2Binding) : RecyclerView.ViewHolder(rowCenterTab2Binding.root){
            val rowCenterTab2Binding: RowCenterTab2Binding

            init {
                this.rowCenterTab2Binding = rowCenterTab2Binding

                this.rowCenterTab2Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterTab2ViewHolder {
            val rowCenterTab2Binding = RowCenterTab2Binding.inflate(layoutInflater)
            val centerTab2ViewHolder = CenterTab2ViewHolder(rowCenterTab2Binding)

            return centerTab2ViewHolder
        }

        override fun getItemCount(): Int {
            return 7
        }

        override fun onBindViewHolder(holder: CenterTab2ViewHolder, position: Int) {
            holder.rowCenterTab2Binding.tvRowCenterTypeTab2.text = "운동 종류"
            holder.rowCenterTab2Binding.tvRowCenterNameTab2.text = "피트니스 센터 이름 $position"
            holder.rowCenterTab2Binding.tvRowCenterLocationTab2.text = "주소, 거리 $position"
            holder.rowCenterTab2Binding.tvRowCenterPriceTab2.text = "가격 ${position * 10000}원"

            holder.rowCenterTab2Binding.tvRowCenterMoreInfoTab2.text = "공지사항 / 후기 점수 / 부가 서비스 등 $position"
        }
    }

}