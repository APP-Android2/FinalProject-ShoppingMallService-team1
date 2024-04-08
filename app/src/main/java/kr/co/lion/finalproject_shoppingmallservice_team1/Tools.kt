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
    MY_PROFILE_FRAGMENT(6, "MyProfileFragment"),
    MY_NOTIFICATION_FRAGMENT(7, "MyNotificationFragment"),
}

enum class HOME_BOTTOM_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_ADDRESS_BOTTOM_MAP_FRAGMENT(0, "현재 위치")
}

enum class HOME_SHOP_FRAGMENT_NAME(var num:Int, var str:String){

    SHOP_CONTAIN_FRAGMENT(0, "장바구니담기")
}

enum class CENTER_FRAGMENT_NAME(var num:Int, var str:String){

    CENTER_A_FRAGMENT(0, "A"),
    CENTER_B_FRAGMENT(1, "B"),
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

    MY_A_FRAGMENT(0, "A"),
    MY_B_FRAGMENT(1, "B"),
}

enum class TRANSFER_MEMBERSHIP_FRAGMENT_NAME(var num:Int, var str:String){
    TRANSFER_MEMBERSHIP_CONTENT_LIST_FRAGMENT(0, "양도회원권 글 목록"),
    TRANSFER_MEMBERSHIP_DETAIL_CONTENT_FRAGMENT(1, "양도회원권 상세 페이지"),
    TRANSFER_MEMBERSHIP_CREATE_CONTENT_FRAGMENT(2, "양도회원권 글 작성"),
    TRANSFER_MEMBERSHIP_EDIT_CONTENT_FRAGMENT(3, "양도회원권 글 수정"),
}