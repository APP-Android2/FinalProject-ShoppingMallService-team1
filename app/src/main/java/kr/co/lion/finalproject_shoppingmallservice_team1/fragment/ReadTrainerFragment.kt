package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowReadTrainerMembershipBinding


class ReadTrainerFragment : Fragment() {

    lateinit var fragmentReadTrainerBinding: FragmentReadTrainerBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadTrainerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_trainer, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbarReadTrainer()

        settingRecyclerView()


        return fragmentReadTrainerBinding.root
    }

    fun settingToolbarReadTrainer(){
        fragmentReadTrainerBinding.apply {
            toolbarReadTrainer.apply {
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    backProcess()
                }
                inflateMenu(R.menu.menu_trainer)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.shopping -> {

                        }
                    }
                    true
                }
            }
        }
    }

    fun backProcess(){
        navigationActivity.removeFragment(NAVIGATION_FRAGMENT_NAME.READ_TRAINER_FRAGMENT)
    }


    // 회원권 RecyclerView 설정
    fun settingRecyclerView(){
        fragmentReadTrainerBinding.apply {
            recyclerViewTrainerPt.apply {
                val membershipData = listOf("10회", "15회", "20회", "25회") // 예시 데이터

                adapter = TrainerMembershipAdapter(membershipData)
                layoutManager = LinearLayoutManager(navigationActivity)
                setHasFixedSize(true)
                addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                    adjustRecyclerViewHeight()
                }

                // 모든 Item의 높이를 측정하여 RecyclerView의 높이를 계산.
                post {
                    adjustRecyclerViewHeight()
                }
            }
        }
    }

    // RecyclerView의 각 Item 크기를 측정.
    fun adjustRecyclerViewHeight() {
        val adapter = fragmentReadTrainerBinding.recyclerViewTrainerPt.adapter
        if (adapter != null && adapter.itemCount > 0) {
            var totalHeight = 0
            for (i in 0 until adapter.itemCount) {
                val view = adapter.createViewHolder(fragmentReadTrainerBinding.recyclerViewTrainerPt, 0).itemView
                adapter.bindViewHolder(adapter.createViewHolder(fragmentReadTrainerBinding.recyclerViewTrainerPt, 0), i)
                view.measure(
                    View.MeasureSpec.makeMeasureSpec(fragmentReadTrainerBinding.recyclerViewTrainerPt.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                totalHeight += view.measuredHeight
            }
            val layoutParams = fragmentReadTrainerBinding.recyclerViewTrainerPt.layoutParams
            layoutParams.height = totalHeight
            fragmentReadTrainerBinding.recyclerViewTrainerPt.layoutParams = layoutParams
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