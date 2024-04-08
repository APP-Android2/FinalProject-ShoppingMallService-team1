package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyCouponBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyCouponBinding

class MyCouponFragment : Fragment() {

    lateinit var fragmentMyCouponBinding: FragmentMyCouponBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyCouponBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_coupon, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        settingRecyclerViewMyCoupon()

        return fragmentMyCouponBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyCouponBinding.apply {
            toolbarMyCoupon.apply {
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    backProcess()
                }
            }
        }
    }

    // 뒤로가기 처리
    private fun backProcess(){
        SystemClock.sleep(200)
        parentFragmentManager.popBackStack()
    }

    // 뒤로가기 처리(단말기)
    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로가기
                backProcess()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    // RecyclerView 설정
    fun settingRecyclerViewMyCoupon(){
        fragmentMyCouponBinding.apply {
            recyclerViewMyCoupon.apply {
                // 어댑터
                adapter = MyCouponRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class MyCouponRecyclerViewAdapter : RecyclerView.Adapter<MyCouponRecyclerViewAdapter.MyCouponViewHolder>() {
        // ViewHolder
        inner class MyCouponViewHolder(rowMyCouponBinding: RowMyCouponBinding) : RecyclerView.ViewHolder(rowMyCouponBinding.root){
            val rowMyCouponBinding: RowMyCouponBinding

            init {
                this.rowMyCouponBinding = rowMyCouponBinding

                this.rowMyCouponBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCouponViewHolder {
            val rowMyCouponBinding = RowMyCouponBinding.inflate(layoutInflater)
            val myCouponViewHolder = MyCouponViewHolder(rowMyCouponBinding)

            return myCouponViewHolder
        }

        override fun getItemCount(): Int {
            return 8
        }

        override fun onBindViewHolder(holder: MyCouponViewHolder, position: Int) {
            holder.rowMyCouponBinding.textView15.text = "쿠폰 제목 ${position}% 할인 쿠폰"
            holder.rowMyCouponBinding.textView12.text = "${position}%"
        }
    }
}