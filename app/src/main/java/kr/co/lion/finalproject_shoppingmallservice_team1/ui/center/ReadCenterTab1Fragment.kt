package kr.co.lion.finalproject_shoppingmallservice_team1.ui.center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadCenterTab1Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowReadTrainerImageBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowReadTrainerMembershipBinding


class ReadCenterTab1Fragment : Fragment() {

    lateinit var fragmentReadCenterTab1Binding: FragmentReadCenterTab1Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadCenterTab1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_center_tab1, container, false)
        fragmentReadCenterTab1Binding.lifecycleOwner = this

        settingRecyclerView()

        return fragmentReadCenterTab1Binding.root
    }

    // RecyclerView 설정
    fun settingRecyclerView(){
        fragmentReadCenterTab1Binding.apply {
            recyclerViewCenterPt.apply {
                val membershipData = listOf("1개월", "2개월", "3개월", "4개월") // 예시 데이터

                adapter = CenterMembershipAdapter(membershipData)
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                    adjustRecyclerViewHeight()
                }

                // 모든 Item의 높이를 측정하여 RecyclerView의 높이를 계산.
                post {
                    adjustRecyclerViewHeight()
                }
            }
            recyclerViewCenterImage.apply {
                val imageList = listOf(R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background)

                adapter = TrainerImageAdapter(imageList)
                layoutManager = GridLayoutManager(context,3)
            }
        }
    }


    // RecyclerView의 각 Item 크기를 측정.
    fun adjustRecyclerViewHeight() {
        val adapter = fragmentReadCenterTab1Binding.recyclerViewCenterPt.adapter
        if (adapter != null && adapter.itemCount > 0) {
            var totalHeight = 0
            for (i in 0 until adapter.itemCount) {
                val view = adapter.createViewHolder(fragmentReadCenterTab1Binding.recyclerViewCenterPt, 0).itemView
                adapter.bindViewHolder(adapter.createViewHolder(fragmentReadCenterTab1Binding.recyclerViewCenterPt, 0), i)
                view.measure(
                    View.MeasureSpec.makeMeasureSpec(fragmentReadCenterTab1Binding.recyclerViewCenterPt.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                totalHeight += view.measuredHeight
            }
            val layoutParams = fragmentReadCenterTab1Binding.recyclerViewCenterPt.layoutParams
            layoutParams.height = totalHeight
            fragmentReadCenterTab1Binding.recyclerViewCenterPt.layoutParams = layoutParams
        }
    }

}


// 회원권 RecyclerView
class CenterMembershipAdapter(val dataList: List<String>) : RecyclerView.Adapter<CenterMembershipViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterMembershipViewHolder {
        // 뷰 홀더를 만들어준다.
        val inflater = LayoutInflater.from(parent.context)
        val rowReadTrainerMembershipBinding = RowReadTrainerMembershipBinding.inflate(inflater, parent, false)

        return CenterMembershipViewHolder(rowReadTrainerMembershipBinding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CenterMembershipViewHolder, position: Int) {
        holder.holderBind(dataList[position])
    }
}

// 회원권 ViewHolder
class CenterMembershipViewHolder(private val rowReadTrainerMembershipBinding: RowReadTrainerMembershipBinding) : RecyclerView.ViewHolder(rowReadTrainerMembershipBinding.root) {
    fun holderBind(data: String) {
        rowReadTrainerMembershipBinding.textViewMembershipCount.text = data
        rowReadTrainerMembershipBinding.textViewMembershipMoney.text = "${data}원"
        rowReadTrainerMembershipBinding.textViewMembershipTotalMoney.text = "총 결제금액${data}원"
    }
}



// 운동센터 사진 RecyclerView 설정
class TrainerImageAdapter(val imageList: List<Int>): RecyclerView.Adapter<TrainerImageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerImageViewHolder {
        // 뷰 홀더를 만들어준다.
        val inflater = LayoutInflater.from(parent.context)
        val rowReadTrainerImageBinding = RowReadTrainerImageBinding.inflate(inflater, parent, false)

        return TrainerImageViewHolder(rowReadTrainerImageBinding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: TrainerImageViewHolder, position: Int) {
        holder.holderBind(imageList[position])
    }
}

// 운동센터 사진 ViewHolder
class TrainerImageViewHolder(private val rowReadTrainerImageBinding: RowReadTrainerImageBinding): RecyclerView.ViewHolder(rowReadTrainerImageBinding.root){

    fun holderBind(imageTest: Int){
        rowReadTrainerImageBinding.trainerDailyImageView.setImageResource(imageTest)
    }
}