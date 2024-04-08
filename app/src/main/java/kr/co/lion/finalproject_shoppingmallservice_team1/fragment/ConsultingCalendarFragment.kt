package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.CONSULTING_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.ConsultingActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentConsultingCalendarBinding

class ConsultingCalendarFragment : Fragment() {

    lateinit var fragmentConsultingCalendarBinding: FragmentConsultingCalendarBinding
    lateinit var consultingActivity: ConsultingActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentConsultingCalendarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_consulting_calendar, container, false)
        consultingActivity = activity as ConsultingActivity

        settingCalendatToolbar()
        settingCalendar()

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

    fun settingCalendar(){
        fragmentConsultingCalendarBinding.apply {
            calendarViewConsulting.apply {
                    setOnDateChangeListener { view, year, month, dayOfMonth ->
                        // 날짜 선택 시 동작하는 코드를 여기에 추가합니다.
                        val selectedDate = "$year/${month + 1}/$dayOfMonth"
                        // 선택된 날짜의 바텀시트를 올린다.
                        showConsultingCalendarBottomSheet()
                    }

            }
        }

    }
    // 시간대를 보여불 BottomSheet를 띄워준다.
    fun showConsultingCalendarBottomSheet(){
        val consultingCalendarBottomFragment = ConsultingCalendarBottomFragment()
        consultingCalendarBottomFragment.show(consultingActivity.supportFragmentManager, "ConsultingCalendarBottomSheet")
    }

}