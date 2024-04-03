package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.finalproject_shoppingmallservice_team1.CONSULTING_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.ConsultingActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentConsultingCalendarBottomBinding

class ConsultingCalendarBottomFragment : BottomSheetDialogFragment() {

    lateinit var fragmentConsultingCalendarBottomBinding: FragmentConsultingCalendarBottomBinding
    lateinit var consultingActivity: ConsultingActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentConsultingCalendarBottomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_consulting_calendar_bottom, container, false)
        consultingActivity = activity as ConsultingActivity

        settingButtonClick()

        return fragmentConsultingCalendarBottomBinding.root
    }


    /**
     *
     *
     */

    // 다이얼로그가 만들어질 때 자동으로 호출되는 메서드
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 다이얼로그를 받는다.
        val dialog = super.onCreateDialog(savedInstanceState)
        // 다이얼로그가 보일때 동작하는 리스너
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            // 높이를 설정한다.
            setBottomSheetHeight(bottomSheetDialog)
        }
        return dialog
    }

    // BottomSheet의 높이를 설정해준다.
    fun setBottomSheetHeight(bottomSheetDialog: BottomSheetDialog){
        // BottomSheet의 기본 뷰 객체를 가져온다
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
        // BottomSheet 높이를 설정할 수 있는 객체를 생성한다.
        val behavior = BottomSheetBehavior.from(bottomSheet)
        // 높이를 설정한다.
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // BottomSheet의 높이를 구한다(화면 액정의 40% 크기)
    fun getBottomSheetDialogHeight() : Int {
        return (getWindowHeight() * 0.5).toInt()
    }

    // 사용자 단말기 액정의 길이를 구해 반환하는 메서드
    fun getWindowHeight() : Int {
        // 화면 크기 정보를 담을 배열 객체
        val displayMetrics = DisplayMetrics()
        // 액정의 가로 세로 길이 정보를 담아준다.
        consultingActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // 세로길이를 반환해준다.
        return displayMetrics.heightPixels
    }

    // 버튼 이벤트
    fun settingButtonClick(){
        fragmentConsultingCalendarBottomBinding.apply {
            consultingCalendarAddButton.apply {
                setOnClickListener {
                    // BottomSheet를 닫기
                    dismiss()
                    backProcess()
                }
            }
        }
    }

    fun backProcess(){
        consultingActivity.removeFragment(CONSULTING_FRAGMENT_NAME.CONSULTING_CALENDAR_FRAGMENT)
    }
}


/**
 * 커스텀 ScrollView 생성 원인.
 * BottomSheet에서 ScrollView 사용 중, 위에서 아래 방향으로 스크롤 시,
 * BottomSheet가 내려가지게 되어, BottomSheet가 사라지는 현상을 방지 하기 위함.
 *
 * 스크롤링 기능을 위해,
 * ScrollView의 onTouchEvent 메서드를 오버라이드하여 사용자의 터치 이벤트를 처리.
 */
class CustomScrollView(context: Context, attrs: AttributeSet?) : ScrollView(context, attrs) {

    var lastY = 0f
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            when (it.action) {
                // 사용자가 터치를 시작.
                MotionEvent.ACTION_DOWN -> {
                    lastY = it.y
                    // 현재 Y좌표를 기록..
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                // 스코롤 동작이 발생.
                MotionEvent.ACTION_MOVE -> {
                    val deltaY = it.y - lastY
                    lastY = it.y
                    // ScrollView가 더 이상 스크롤 할 수 없는 경우.
                    if (canScrollVertically(deltaY)) {
                        // 부모(BottomSheet)의 View로 터치 이벤트를 전달.
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    fun canScrollVertically(delta: Float): Boolean {
        return computeVerticalScrollRange() > height || delta < 0
    }
}