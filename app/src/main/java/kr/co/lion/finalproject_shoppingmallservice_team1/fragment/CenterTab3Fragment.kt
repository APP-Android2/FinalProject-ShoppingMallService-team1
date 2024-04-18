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
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCenterTab3Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCenterTab3Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity

class CenterTab3Fragment : Fragment() {

    lateinit var fragmentCenterTab3Binding: FragmentCenterTab3Binding
    lateinit var navigationActivity: NavigationActivity

    var isImageClick = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCenterTab3Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_center_tab3, container, false)
        navigationActivity = activity as NavigationActivity

        settingRecyclerViewCenterTab3()

        return fragmentCenterTab3Binding.root
    }

    // RecyclerView 구성
    fun settingRecyclerViewCenterTab3(){
        fragmentCenterTab3Binding.apply {
            recyclerViewCenterTab3.apply {
                // 어댑터
                adapter = CenterTab3RecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class CenterTab3RecyclerViewAdapter : RecyclerView.Adapter<CenterTab3RecyclerViewAdapter.CenterTab3ViewHolder>() {

        // ViewHolder
        inner class CenterTab3ViewHolder(rowCenterTab3Binding: RowCenterTab3Binding) : RecyclerView.ViewHolder(rowCenterTab3Binding.root){
            val rowCenterTab3Binding: RowCenterTab3Binding

            init {
                this.rowCenterTab3Binding = rowCenterTab3Binding

                this.rowCenterTab3Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )

                // 찜 버튼 설정
                this.rowCenterTab3Binding.buttonCenterPickTab3.setOnClickListener {
                    isImageClick = !isImageClick
                    updateImageButton()
                }
            }

            // 찜 버튼 클릭에 따른 이미지 변경
            fun updateImageButton(){
                rowCenterTab3Binding.apply {
                    if(isImageClick){
                        buttonCenterPickTab3.setImageResource(R.drawable.favorite)
                    } else {
                        buttonCenterPickTab3.setImageResource(R.drawable.favorite_fill)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterTab3ViewHolder {
            val rowCenterTab3Binding = RowCenterTab3Binding.inflate(layoutInflater)
            val centerTab3ViewHolder = CenterTab3ViewHolder(rowCenterTab3Binding)

            return centerTab3ViewHolder
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: CenterTab3ViewHolder, position: Int) {
            holder.rowCenterTab3Binding.tvRowCenterTypeTab3.text = "운동 종류"
            holder.rowCenterTab3Binding.tvRowCenterNameTab3.text = "수영 센터 이름 $position"
            holder.rowCenterTab3Binding.tvRowCenterLocationTab3.text = "주소, 거리 $position"
            holder.rowCenterTab3Binding.tvRowCenterPriceTab3.text = "가격 ${position * 10000}원"

            holder.rowCenterTab3Binding.tvRowCenterMoreInfoTab3.text = "공지사항 / 후기 점수 / 부가 서비스 등 $position"
        }
    }
}