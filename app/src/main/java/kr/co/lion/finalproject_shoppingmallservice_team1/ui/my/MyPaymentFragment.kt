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
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyPaymentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyPaymentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.my.viewmodel.MyPaymentViewModel

class MyPaymentFragment : Fragment() {

    lateinit var fragmentMyPaymentBinding: FragmentMyPaymentBinding
    lateinit var myPaymentViewModel: MyPaymentViewModel
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyPaymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_payment, container, false)
        myPaymentViewModel = MyPaymentViewModel()
        fragmentMyPaymentBinding.myPaymentViewModel = myPaymentViewModel
        fragmentMyPaymentBinding.lifecycleOwner = this@MyPaymentFragment

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        settingChipMyPayment()
        settingRecyclerViewMyPayment()

        return fragmentMyPaymentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MyPaymentFragment 가 실행될 때 하단바가 보이지 않도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()

        // MyPaymentFragment 가 제거될 때 하단바가 보이도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = true
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

    private fun settingChipMyPayment(){
        fragmentMyPaymentBinding.apply {
            // 날짜 칩메뉴
            chipMyPaymentDate.apply {
                setOnClickListener {
                    val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)

                    val popup = PopupMenu(contextWrapper, this)

                    popup.inflate(R.menu.menu_my_payment_term)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId){
                            R.id.menuMyPaymentTermNewly -> {
                                text = "최신순"
                            }
                            R.id.menuMyPaymentTermLastly -> {
                                text = "오래된순"
                            }
                        }
                        true
                    }

                    popup.show()
                }
            }

            // 타입 칩메뉴
            chipMyPaymentType.apply {
                setOnClickListener {
                    val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)

                    val popup = PopupMenu(contextWrapper, this)

                    popup.inflate(R.menu.menu_my_payment_type)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId){
                            R.id.menuMyPaymentTypeAll -> {
                                text = "전체"
                            }
                            R.id.menuMyPaymentTypePT -> {
                                text = "PT"
                            }
                            R.id.menuMyPaymentTypeCenter -> {
                                text = "운동센터"
                            }
                        }
                        true
                    }

                    popup.show()
                }
            }
        }
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