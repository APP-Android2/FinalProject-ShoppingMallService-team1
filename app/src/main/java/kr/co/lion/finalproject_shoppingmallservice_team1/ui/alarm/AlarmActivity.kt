package kr.co.lion.finalproject_shoppingmallservice_team1.ui.alarm

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityAlarmBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowHomeAlarmBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.model.Alarm

class AlarmActivity : AppCompatActivity() {
    lateinit var activityAlarmBinding:ActivityAlarmBinding
    lateinit var navigationActivity: NavigationActivity
    var alarmList = mutableListOf<Alarm>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAlarmBinding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(activityAlarmBinding.root)


        settingToolbar()
        getDataList()
        settingRecyclerViewAlarm()
    }

    fun settingToolbar() {
        activityAlarmBinding.apply {
            toolbarAlarm.apply {
                setNavigationIcon(R.drawable.close)

                setNavigationOnClickListener {
                    finish()
                }

                inflateMenu(R.menu.empty_menu)
            }
        }
    }


    // 알림 목록
    fun settingRecyclerViewAlarm(){
        activityAlarmBinding.apply {
            recyclerViewHomeAlarm.apply {
                adapter = AlarmRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@AlarmActivity)

                val deco = MaterialDividerItemDecoration(this@AlarmActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }
    inner class AlarmRecyclerViewAdapter: RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmViewHolder>(){
        inner class AlarmViewHolder(rowHomeAlarmBinding: RowHomeAlarmBinding): RecyclerView.ViewHolder(rowHomeAlarmBinding.root){
            val rowHomeAlarmBinding: RowHomeAlarmBinding

            init {
                this.rowHomeAlarmBinding = rowHomeAlarmBinding
                rowHomeAlarmBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
            val rowHomeAlarmBinding = RowHomeAlarmBinding.inflate(layoutInflater)
            val alarmViewHolder = AlarmViewHolder(rowHomeAlarmBinding)
            return alarmViewHolder
        }

        override fun getItemCount(): Int {
            return alarmList.size
        }

        override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
            holder.rowHomeAlarmBinding.textViewHomeAlarmContent.text = alarmList[position].title
            holder.rowHomeAlarmBinding.textViewHomeAlarmDate.text = alarmList[position].date
        }
    }

    fun getDataList(){
        CoroutineScope(Dispatchers.Main).launch {
            alarmList = AlarmDao.getAlarmList()

            activityAlarmBinding.recyclerViewHomeAlarm.adapter?.notifyDataSetChanged()
        }
    }
}