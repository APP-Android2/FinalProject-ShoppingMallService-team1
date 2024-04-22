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

    // 회원권 샘플 데이터
    val membershipData = listOf("1개월", "2개월", "3개월", "4개월")

    // 사진 샘플 데이터
    val imageList = listOf(R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadCenterTab1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_center_tab1, container, false)
        fragmentReadCenterTab1Binding.lifecycleOwner = this

        settingRecyclerView()

        return fragmentReadCenterTab1Binding.root
    }

    // RecyclerView 설정
    fun settingRecyclerView(){
        fragmentReadCenterTab1Binding.apply {
            recyclerViewCenterMemberShip.apply {
                adapter = CenterMembershipAdapter(membershipData)
                layoutManager = LinearLayoutManager(context)

            }
            recyclerViewCenterImage.apply {
                adapter = CenterImageAdapter(imageList)
                layoutManager = GridLayoutManager(context,3)
            }
        }
    }
}


// 회원권 RecyclerView
class CenterMembershipAdapter(val dataList: List<String>) : RecyclerView.Adapter<CenterMembershipViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterMembershipViewHolder {
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
class CenterImageAdapter(val imageList: List<Int>): RecyclerView.Adapter<CenterImageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rowReadTrainerImageBinding = RowReadTrainerImageBinding.inflate(inflater, parent, false)

        return CenterImageViewHolder(rowReadTrainerImageBinding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: CenterImageViewHolder, position: Int) {
        holder.holderBind(imageList[position])
    }
}

// 운동센터 사진 ViewHolder
class CenterImageViewHolder(private val rowReadTrainerImageBinding: RowReadTrainerImageBinding): RecyclerView.ViewHolder(rowReadTrainerImageBinding.root){

    fun holderBind(imageTest: Int){
        rowReadTrainerImageBinding.trainerDailyImageView.setImageResource(imageTest)
    }
}