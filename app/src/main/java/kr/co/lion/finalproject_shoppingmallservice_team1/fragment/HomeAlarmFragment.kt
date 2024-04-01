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
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeAlarmBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowAddressBottomBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowHomeAlarmBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel.HomeAlarmViewModel

class HomeAlarmFragment : Fragment() {
    lateinit var fragmentHomeAlarmBinding: FragmentHomeAlarmBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var homeAlarmViewModel: HomeAlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeAlarmBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_alarm, container, false)
        homeAlarmViewModel = HomeAlarmViewModel()
        fragmentHomeAlarmBinding.homeAlarmViewModel = homeAlarmViewModel
        fragmentHomeAlarmBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingRecyclerViewHomeAlarm()

        return fragmentHomeAlarmBinding.root
    }

    fun settingToolbar() {
        fragmentHomeAlarmBinding.apply {
            toolbarAlarm.apply {
                setNavigationIcon(R.drawable.arrow_back)

                setNavigationOnClickListener {
                    SystemClock.sleep(200)
                    parentFragmentManager.popBackStack()
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }

    fun settingRecyclerViewHomeAlarm(){
        fragmentHomeAlarmBinding.apply {
            recyclerViewHomeAlarm.apply {
                adapter = HomeAlarmRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(navigationActivity)

                val deco = MaterialDividerItemDecoration(navigationActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }
    inner class HomeAlarmRecyclerViewAdapter:RecyclerView.Adapter<HomeAlarmRecyclerViewAdapter.HomeAlarmViewHolder>(){
        inner class HomeAlarmViewHolder(rowHomeAlarmBinding: RowHomeAlarmBinding):RecyclerView.ViewHolder(rowHomeAlarmBinding.root){
            val rowHomeAlarmBinding:RowHomeAlarmBinding

            init {
                this.rowHomeAlarmBinding = rowHomeAlarmBinding
                rowHomeAlarmBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAlarmViewHolder {
            val rowHomeAlarmBinding = RowHomeAlarmBinding.inflate(layoutInflater)
            val homeAlarmViewHolder = HomeAlarmViewHolder(rowHomeAlarmBinding)
            return homeAlarmViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: HomeAlarmViewHolder, position: Int) {
            holder.rowHomeAlarmBinding.textViewHomeAlarmContent.text = "더 좋은 서비스 제공을 위해 업데이트 예정이에요."
            holder.rowHomeAlarmBinding.textViewHomeAlarmDate.text = "2024-3-29"
        }
    }

}