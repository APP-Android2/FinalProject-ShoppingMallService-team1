package kr.co.lion.finalproject_shoppingmallservice_team1

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityAlarmBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowHomeAlarmBinding

class AlarmActivity : AppCompatActivity() {
    lateinit var activityAlarmBinding:ActivityAlarmBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAlarmBinding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(activityAlarmBinding.root)


        settingToolbar()
        settingRecyclerViewAlarm()
    }

    fun settingToolbar() {
        activityAlarmBinding.apply {
            toolbarAlarm.apply {
                setNavigationIcon(R.drawable.arrow_back)

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
            return 10
        }

        override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
            holder.rowHomeAlarmBinding.textViewHomeAlarmContent.text = "더 좋은 서비스 제공을 위해 업데이트 예정이에요."
            holder.rowHomeAlarmBinding.textViewHomeAlarmDate.text = "2024-3-29"
        }
    }
}