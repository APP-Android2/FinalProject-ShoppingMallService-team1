package kr.co.lion.finalproject_shoppingmallservice_team1.ui.center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.CenterDao
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCenterTab1Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCenterTab1Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.model.Center
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity

class CenterTab1Fragment : Fragment() {

    lateinit var fragmentCenterTab1Binding: FragmentCenterTab1Binding
    lateinit var navigationActivity: NavigationActivity

    var centerList = mutableListOf<Center>()

    var isImageClick = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCenterTab1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_center_tab1, container, false)
        navigationActivity = activity as NavigationActivity

        getDataList()
        settingRecyclerViewCenterTab1()


        return fragmentCenterTab1Binding.root
    }

    // RecyclerView 설정
    fun settingRecyclerViewCenterTab1(){
        fragmentCenterTab1Binding.apply {
            recyclerViewCenterTab1.apply {
                // 어댑터
                adapter = CenterTab1RecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class CenterTab1RecyclerViewAdapter : RecyclerView.Adapter<CenterTab1RecyclerViewAdapter.CenterTab1ViewHolder>() {
        inner class CenterTab1ViewHolder(rowCenterTab1Binding: RowCenterTab1Binding) : RecyclerView.ViewHolder(rowCenterTab1Binding.root){
            val rowCenterTab1Binding: RowCenterTab1Binding

            init {
                this.rowCenterTab1Binding = rowCenterTab1Binding

                this.rowCenterTab1Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )

                // 찜 버튼 설정
                this.rowCenterTab1Binding.buttonCenterPickTab1.setOnClickListener {
                    isImageClick = !isImageClick
                    updateImageButton()
                }
                // 게시글 클릭 설정
                this.rowCenterTab1Binding.apply {
                    itemCenterTab1Post.setOnClickListener {
                        navigationActivity.readCenterRequest()
                    }
                }
            }

            // 찜 버튼 클릭에 따른 이미지 변경
            fun updateImageButton(){
                rowCenterTab1Binding.apply {
                    if(isImageClick){
                        buttonCenterPickTab1.setImageResource(R.drawable.favorite)
                    } else {
                        buttonCenterPickTab1.setImageResource(R.drawable.favorite_fill)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterTab1ViewHolder {
            val rowCenterTab1Binding = RowCenterTab1Binding.inflate(layoutInflater)
            val centerTab1ViewHolder = CenterTab1ViewHolder(rowCenterTab1Binding)

            return centerTab1ViewHolder
        }

        override fun getItemCount(): Int {
            return centerList.size
        }

        override fun onBindViewHolder(holder: CenterTab1ViewHolder, position: Int) {
            holder.rowCenterTab1Binding.tvRowCenterTypeTab1.text = "운동 종류"
            holder.rowCenterTab1Binding.tvRowCenterNameTab1.text = centerList[position].centerName
            holder.rowCenterTab1Binding.tvRowCenterLocationTab1.text = centerList[position].centerLocation
            holder.rowCenterTab1Binding.tvRowCenterPriceTab1.text = "가격 ${position * 10000}원"

            holder.rowCenterTab1Binding.tvRowCenterMoreInfoTab1.text = "공지사항 / 후기 점수 / 부가 서비스 등 $position"
        }
    }

    fun getDataList(){
        CoroutineScope(Dispatchers.Main).launch {
            centerList = CenterDao.getCenterList()

            fragmentCenterTab1Binding.recyclerViewCenterTab1.adapter?.notifyDataSetChanged()
        }
    }
}