package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyPaymentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyPaymentBinding

class MyPaymentFragment : Fragment() {

    lateinit var fragmentMyPaymentBinding: FragmentMyPaymentBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyPaymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_payment, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingRecyclerViewMyPayment()

        return fragmentMyPaymentBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyPaymentBinding.apply {
            toolbarMyPayment.apply {
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    backProcess()
                }
            }
        }
    }

    // 뒤로가기 처리
    fun backProcess(){
        SystemClock.sleep(200)
        parentFragmentManager.popBackStack()
    }

    // RecyclerView 설정
    fun settingRecyclerViewMyPayment(){
        fragmentMyPaymentBinding.apply {
            recyclerViewMyPayment.apply {
                // 어댑터
                adapter = MyPaymentRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class MyPaymentRecyclerViewAdapter : RecyclerView.Adapter<MyPaymentRecyclerViewAdapter.MyPaymentViewHolder>() {
        // ViewHolder
        inner class MyPaymentViewHolder(rowMyPaymentBinding: RowMyPaymentBinding) : RecyclerView.ViewHolder(rowMyPaymentBinding.root){
            val rowMyPaymentBinding: RowMyPaymentBinding

            init {
                this.rowMyPaymentBinding = rowMyPaymentBinding

                this.rowMyPaymentBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPaymentViewHolder {
            val rowMyPaymentBinding = RowMyPaymentBinding.inflate(layoutInflater)
            val myPaymentViewHolder = MyPaymentViewHolder(rowMyPaymentBinding)

            return myPaymentViewHolder
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: MyPaymentViewHolder, position: Int) {
            holder.rowMyPaymentBinding.tvRowMyPaymentProductName.text = "제품명 $position"
            holder.rowMyPaymentBinding.tvRowMyPaymentCenterName.text = "운동 센터 이름 $position"
            holder.rowMyPaymentBinding.tvRowMyPaymentDay.text = "2024-04-03"
        }
    }
}