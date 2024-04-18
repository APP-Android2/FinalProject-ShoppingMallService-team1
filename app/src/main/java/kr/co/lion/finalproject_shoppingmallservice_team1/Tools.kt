package kr.co.lion.finalproject_shoppingmallservice_team1

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.concurrent.thread

class Tools {
    companion object{

        // 뷰에 포커스를 주고 키보드를 올린다.
        fun showSoftInput(context: Context, view: View){
            // 뷰에 포커스를 준다.
            view.requestFocus()
            thread {
                // 딜레이
                SystemClock.sleep(200)
                // 키보드 관리 객체를 가져온다.
                val inputMethodManger = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                // 키보드를 올린다.
                inputMethodManger.showSoftInput(view, 0)
            }
        }

        // 키보드를 내려주고 포커스를 제거한다.
        fun hideSoftInput(activity: Activity){
            // 포커스를 가지고 있는 뷰가 있다면..
            if(activity.window.currentFocus != null){
                // 키보드 관리 객체를 가져온다.
                val inputMethodManger = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                // 키보드를 내려준다.
                inputMethodManger.hideSoftInputFromWindow(activity.window.currentFocus?.windowToken, 0)
                // 포커스를 제거해준다.
                activity.window.currentFocus?.clearFocus()
            }
        }

        // 입력 요소가 비어있을때 보여줄 다이얼로그를 구성하는 메서드
        fun showErrorDialog(context: Context, view: View, title:String, message:String){
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle(title)
            materialAlertDialogBuilder.setMessage(message)
            materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                showSoftInput(context, view)
            }
            materialAlertDialogBuilder.show()
        }

    }
}

enum class LOGIN_FRAGMENT_NAME(var num:Int, var str:String){

    LOGIN_FRAGMENT(0, "로그인"),
    LOGIN_SIGNUP_FRAGMENT(1, "회원가입"),
}

enum class LOGIN_SIGNUP_FRAGMENT_NAME(var num:Int, var str:String){

    LOGIN_SIGNUP1_FRAGMENT(0, "회원가입1"),
    LOGIN_SIGNUP2_FRAGMENT(1, "회원가입2"),
}

enum class NAVIGATION_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_FRAGMENT(0, "홈"),
    CENTER_FRAGMENT(1, "운동 센터"),
    TRAINER_FRAGMENT(2, "트레이너"),
    COMMUNITY_FRAGMENT(3, "커뮤니티"),
    MY_FRAGMENT(4, "MY"),
}

enum class HOME_BOTTOM_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_ADDRESS_BOTTOM_MAP_FRAGMENT(0, "현재 위치")
}

enum class HOME_SHOP_FRAGMENT_NAME(var num:Int, var str:String){

    SHOP_CONTAIN_FRAGMENT(0, "장바구니담기")
}

enum class CENTER_FRAGMENT_NAME(var num:Int, var str:String){

    READ_CENTER_FRAGMENT(0,"ReadCenterFragment"),
    READ_CENTER_TAB1_FRAGMENT(1, "ReadCenterTab1Fragment"),
    READ_CENTER_TAB2_FRAGMENT(2, "ReadCenterTab2Fragment"),
    READ_CENTER_TAB3_FRAGMENT(3, "ReadCenterTab3Fragment"),
}

enum class CENTER_TAB_NAME(var num: Int, var str: String){

    CENTER_TAB1_FRAGMENT(0, "헬스"),
    CENTER_TAB2_FRAGMENT(1, "필라테스"),
    CENTER_TAB3_FRAGMENT(2, "수영"),
}

enum class TRAINER_FRAGMENT_NAME(var num:Int, var str:String){

    READ_TRAINER_FRAGMENT(0, "ReadTrainerFragment"),
    READ_TRAINER_TAB1_FRAGMENT(1, "ReadTrainerTab1Fragment"),
    READ_TRAINER_TAB2_FRAGMENT(2, "ReadTrainerTab2Fragment"),
    READ_TRAINER_TAB3_FRAGMENT(3, "ReadTrainerTab3Fragment"),
}

enum class CONSULTING_FRAGMENT_NAME(var num: Int, var str:String){

    CONSULTING_CALENDAR_FRAGMENT(0, "ConsultingCalendarFragment"),
    CONSULTING_A(1, "A")
}

enum class COMMUNITY_FRAGMENT_NAME(var num:Int, var str:String){

    COMMUNITY_MYCONTENT_FRAGMENT(0, "CommunityMyContentFragment"),
    COMMUNITY_COMMENTCONTENT_FRAGMENT(1, "CommunityCommentContentFragment"),
    COMMUNITY_LIKECONTENT_FRAGMENT(2, "CommunityLikeContentFragment")
}

enum class MY_FRAGMENT_NAME(var num:Int, var str:String){

    MY_PROFILE_FRAGMENT(0, "프로필"),
    MY_POINT_FRAGMENT(1, "포인트"),
    MY_COUPON_FRAGMENT(2, "쿠폰"),
    MY_MEMBERSHIP_FRAGMENT(3, "회원권"),
    MY_REVIEW_FRAGMENT(4, "리뷰 관리"),
    MY_PICK_FRAGMENT(5, "찜"),
    MY_VISIT_CONSULTATION_FRAGMENT(6, "방문상담 신청 내역"),
    MY_PAYMENT_FRAGMENT(7, "결제 내역"),
    // 고객센터 프래그먼트
    MY_FAQ_FRAGMENT(9, "FAQ"),
    MY_SETTING_FRAGMENT(10, "설정"),
}

enum class MY_REVIEW_TAB_NAME(var num: Int, var str: String){

    MY_REVIEW_TAB1_FRAGMENT(0, "체육시설 리뷰"),
    MY_REVIEW_TAB2_FRAGMENT(1, "트레이너 리뷰"),
}

enum class MY_PICK_TAB_NAME(var num: Int, var str: String) {

    MY_PICK_TAB1_FRAGMENT(0, "트레이너 찜"),
    MY_PICK_TAB2_FRAGMENT(1, "체육시설 찜"),
}
enum class TRANSFER_MEMBERSHIP_FRAGMENT_NAME(var num:Int, var str:String){
    TRANSFER_MEMBERSHIP_CONTENT_LIST_FRAGMENT(0, "양도회원권 글 목록"),
    TRANSFER_MEMBERSHIP_DETAIL_CONTENT_FRAGMENT(1, "양도회원권 상세 페이지"),
    TRANSFER_MEMBERSHIP_CREATE_CONTENT_FRAGMENT(2, "양도회원권 글 작성"),
    TRANSFER_MEMBERSHIP_EDIT_CONTENT_FRAGMENT(3, "양도회원권 글 수정"),
}