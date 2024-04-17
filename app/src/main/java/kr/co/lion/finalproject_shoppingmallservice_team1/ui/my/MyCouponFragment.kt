package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my

import android.os.Bundle
import android.os.SystemClock
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyCouponBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyCouponBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.model.Coupon
import kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel.MyCouponViewModel

class MyCouponFragment : Fragment() {

    lateinit var fragmentMyCouponBinding: FragmentMyCouponBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var myCouponViewModel: MyCouponViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyCouponBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_coupon, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        settingChip()
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

    private fun settingChip(){
        fragmentMyCouponBinding.chipCoupon.apply {
            setOnClickListener {
                val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)

                val  popup = PopupMenu(contextWrapper, this)
                popup.inflate(R.menu.menu_coupon_chip)
                popup.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemCouponEntire -> {
                            text = "전체"
                        }
                        R.id.menuItemCouponOwn -> {
                            text = "보유한 쿠폰"
                        }
                        R.id.menuItemCouponDown -> {
                            text = "다운로드 가능한 쿠폰"
                        }
                    }
                    true
                }
                popup.show()
            }
        }
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
            val myCouponViewModel = MyCouponViewModel()

            init {
                this.rowMyCouponBinding = rowMyCouponBinding
                this.rowMyCouponBinding.myCouponViewModel = myCouponViewModel

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
            val coupon = Coupon("", "", "재결제 쿠폰", "", 20, true, "",
                "", 7, "")
            holder.myCouponViewModel.tvRowMyCouponTitle.value = coupon.name
            holder.myCouponViewModel.tvRowMyCouponUntilDays.value = "남은 기간 ${coupon.remainingPeriod}일 남음"
            holder.myCouponViewModel.tvRowMyCouponSale.value = "${coupon.discountRate}%"
        }
    }
}