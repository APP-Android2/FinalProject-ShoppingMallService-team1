package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyVisitConsultationBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyVisitConsultationBinding

class MyVisitConsultationFragment : Fragment() {

    lateinit var fragmentMyVisitConsultationBinding: FragmentMyVisitConsultationBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyVisitConsultationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_visit_consultation, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        settingRecyclerViewMyVisitConsultation()

        return fragmentMyVisitConsultationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MyVisitConsultationFragment 가 실행될 때 하단바가 보이지 않도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()

        // MyVisitConsultationFragment 가 제거될 때 하단바가 보이도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = true
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyVisitConsultationBinding.apply {
            toolbarMyVisitConsultation.apply {
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
    fun settingRecyclerViewMyVisitConsultation(){
        fragmentMyVisitConsultationBinding.apply {
            recyclerViewMyVisitConsultation.apply {
                // 어댑터
                adapter = MyVisitConsultationRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class MyVisitConsultationRecyclerViewAdapter : RecyclerView.Adapter<MyVisitConsultationRecyclerViewAdapter.MyVisitConsultationViewHolder>() {
        // ViewHodler
        inner class MyVisitConsultationViewHolder(rowMyVisitConsultationBinding: RowMyVisitConsultationBinding) : RecyclerView.ViewHolder(rowMyVisitConsultationBinding.root){
            val rowMyVisitConsultationBinding: RowMyVisitConsultationBinding

            init {
                this.rowMyVisitConsultationBinding = rowMyVisitConsultationBinding

                this.rowMyVisitConsultationBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVisitConsultationViewHolder {
            val rowMyVisitConsultationBinding = RowMyVisitConsultationBinding.inflate(layoutInflater)
            val myVisitConsultationViewHolder = MyVisitConsultationViewHolder(rowMyVisitConsultationBinding)

            return myVisitConsultationViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: MyVisitConsultationViewHolder, position: Int) {
            holder.rowMyVisitConsultationBinding.tvRowMyVisitConsultationCenterName.text = "운동 센터 $position"
            holder.rowMyVisitConsultationBinding.tvRowMyVisitConsultationTime.text = "예약 시간 $position"
        }
    }
}