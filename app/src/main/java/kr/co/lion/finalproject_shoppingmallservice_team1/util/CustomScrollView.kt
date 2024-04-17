package kr.co.lion.finalproject_shoppingmallservice_team1.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

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