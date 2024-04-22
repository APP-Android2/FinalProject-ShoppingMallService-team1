package kr.co.lion.finalproject_shoppingmallservice_team1.ui.visitconsulting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.CONSULTING_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentConsultingCalendarBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ConsultingCalendarFragment : Fragment() {

    lateinit var fragmentConsultingCalendarBinding: FragmentConsultingCalendarBinding
    lateinit var consultingActivity: ConsultingActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentConsultingCalendarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_consulting_calendar, container, false)
        consultingActivity = activity as ConsultingActivity

        settingCalendatToolbar()
        settingDate()

        return fragmentConsultingCalendarBinding.root
    }

    /**
     * 함수 정리 (작성 순서)
     * 1. 툴바 설정 함수
     * 2. BackStack에 올라간 Fragment 삭제 함수
     * 3. 캘린더 기능 함수
     */

    fun settingCalendatToolbar(){
        fragmentConsultingCalendarBinding.apply {
            toolbarCalendar.apply {
                toolbarCalendar.apply {
                    setNavigationIcon(R.drawable.arrow_back)
                    setNavigationOnClickListener {
                        backProcess()
                    }
                }
            }
        }
    }

    fun backProcess(){
        consultingActivity.removeFragment(CONSULTING_FRAGMENT_NAME.CONSULTING_CALENDAR_FRAGMENT)
    }

    fun settingDate(){
        fragmentConsultingCalendarBinding.apply {
            calendarViewConsulting.apply {
                // 현재 시간을 Long 값으로 구해 CalendarView에 설정해준다.
                setOnDateChangeListener { view, year, month, dayOfMonth ->
                    // 날짜 선택 시 동작하는 코드를 여기에 추가합니다.
                    var selectedDate:String
                    selectedDate = "$year-${month + 1}-$dayOfMonth"

                    if (month + 1 < 10 || dayOfMonth < 10){
                        if (month + 1 < 10 && dayOfMonth < 10){
                            selectedDate = "$year-0${month + 1}-0$dayOfMonth"
                        }
                        else if (month + 1 < 10){
                            selectedDate = "$year-0${month + 1}-$dayOfMonth"
                        }
                        else if(dayOfMonth < 10){
                            selectedDate = "$year-${month + 1}-0$dayOfMonth"
                        }
                    }
                    Log.d("ConsultActivity", "$selectedDate")
                    val bundle = Bundle()
                    bundle.putString("currentDate", selectedDate)
                    parentFragmentManager.setFragmentResult("consultingDate", bundle)

                }

            }
        }
        settingDoneBtn()
    }
    fun settingDoneBtn(){
        fragmentConsultingCalendarBinding.consultingDateAddButton.setOnClickListener {
            backProcess()
        }

    }

}