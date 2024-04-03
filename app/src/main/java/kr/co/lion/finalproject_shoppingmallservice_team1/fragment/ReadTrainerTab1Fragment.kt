package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

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
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerTab1Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowReadTrainerImageBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowReadTrainerMembershipBinding


class ReadTrainerTab1Fragment : Fragment() {

    lateinit var fragmentReadTrainerTab1Binding: FragmentReadTrainerTab1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadTrainerTab1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_trainer_tab1, container, false)
        fragmentReadTrainerTab1Binding.lifecycleOwner = this

        settingRecyclerView()

        return fragmentReadTrainerTab1Binding.root
    }


    // RecyclerView 설정
    fun settingRecyclerView(){
        fragmentReadTrainerTab1Binding.apply {
            recyclerViewTrainerPt.apply {
                val membershipData = listOf("10회", "15회", "20회", "25회") // 예시 데이터

                adapter = TrainerMembershipAdapter(membershipData)
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
            recyclerViewTrainerImage.apply {
                val imageList = listOf(R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background)

                adapter = TrainerImageAdapter(imageList)
                layoutManager = GridLayoutManager(context,3)
            }
        }
    }


    // RecyclerView의 각 Item 크기를 측정.
    fun adjustRecyclerViewHeight() {
        val adapter = fragmentReadTrainerTab1Binding.recyclerViewTrainerPt.adapter
        if (adapter != null && adapter.itemCount > 0) {
            var totalHeight = 0
            for (i in 0 until adapter.itemCount) {
                val view = adapter.createViewHolder(fragmentReadTrainerTab1Binding.recyclerViewTrainerPt, 0).itemView
                adapter.bindViewHolder(adapter.createViewHolder(fragmentReadTrainerTab1Binding.recyclerViewTrainerPt, 0), i)
                view.measure(
                    View.MeasureSpec.makeMeasureSpec(fragmentReadTrainerTab1Binding.recyclerViewTrainerPt.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                totalHeight += view.measuredHeight
            }
            val layoutParams = fragmentReadTrainerTab1Binding.recyclerViewTrainerPt.layoutParams
            layoutParams.height = totalHeight
            fragmentReadTrainerTab1Binding.recyclerViewTrainerPt.layoutParams = layoutParams
        }
    }

}



// 회원권 RecyclerView
class TrainerMembershipAdapter(val dataList: List<String>) : RecyclerView.Adapter<TrainerMembershipViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerMembershipViewHolder {
        // 뷰 홀더를 만들어준다.
        val inflater = LayoutInflater.from(parent.context)
        val rowReadTrainerMembershipBinding = RowReadTrainerMembershipBinding.inflate(inflater, parent, false)

        return TrainerMembershipViewHolder(rowReadTrainerMembershipBinding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TrainerMembershipViewHolder, position: Int) {
        holder.holderBind(dataList[position])
    }
}

// 회원권 ViewHolder
class TrainerMembershipViewHolder(private val rowReadTrainerMembershipBinding: RowReadTrainerMembershipBinding) : RecyclerView.ViewHolder(rowReadTrainerMembershipBinding.root) {
    fun holderBind(data: String) {
        rowReadTrainerMembershipBinding.textViewMembershipCount.text = data
        rowReadTrainerMembershipBinding.textViewMembershipMoney.text = "${data}원"
        rowReadTrainerMembershipBinding.textViewMembershipTotalMoney.text = "총 결제금액${data}원"
    }
}



// 트레이너 사진 RecyclerView 설정
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

// 트레이너 사진 ViewHolder
class TrainerImageViewHolder(private val rowReadTrainerImageBinding: RowReadTrainerImageBinding): RecyclerView.ViewHolder(rowReadTrainerImageBinding.root){

    fun holderBind(imageTest: Int){
        rowReadTrainerImageBinding.trainerDailyImageView.setImageResource(imageTest)
    }
}
