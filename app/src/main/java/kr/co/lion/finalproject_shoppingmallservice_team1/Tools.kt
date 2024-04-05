package kr.co.lion.finalproject_shoppingmallservice_team1

class Tools {
    companion object{

    }
}

enum class MAIN_FRAGMENT_NAME(var num:Int, var str:String){

    MAIN_LOGO_FRAGMENT(0, "메인 로고"),
    MEMBER_EXPLANATION_FRAGMENT(1, "회원 설명"),
}

enum class NAVIGATION_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_FRAGMENT(0, "홈"),
    CENTER_FRAGMENT(1, "운동 센터"),
    TRAINER_FRAGMENT(2, "트레이너"),
    COMMUNITY_FRAGMENT(3, "커뮤니티"),
    MY_FRAGMENT(4, "MY"),
    READ_TRAINER_FRAGMENT(5, "ReadTrainerFragment"),
}

enum class HOME_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_ALARM_FRAGMENT(0, "알림"),
    HOME_CHAT_FRAGMENT(1, "쪽지"),
    HOME_SHOP_FRAGMENT(2, "장바구니")

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

enum class COMMUNITY_FRAGMENT_NAME(var num:Int, var str:String){

    COMMUNITY_A_FRAGMENT(0, "A"),
    COMMUNITY_B_FRAGMENT(1, "B"),
}

enum class MY_FRAGMENT_NAME(var num:Int, var str:String){

    MY_PROFILE_FRAGMENT(0, "프로필"),
    // 포인트
    MY_COUPON_FRAGMENT(2, "쿠폰"),
    MY_MEMBERSHIP_FRAGMENT(3, "회원권"),
    MY_REVIEW_FRAGMENT(4, "리뷰 관리"),
    MY_PICK_FRAGMENT(5, "찜"),
    // 공지/이벤트
    MY_PAYMENT_FRAGMENT(7, "결제 내역"),
    // 고객센터 프래그먼트
    MY_FAQ_FRAGMENT(9, "FAQ"),
    MY_SETTING_FRAGMENT(10, "설정"),
}

enum class MY_REVIEW_TAB_NAME(var num: Int, var str: String){

    MY_REVIEW_TAB1_FRAGMENT(0, "체육시설 리뷰"),
    MY_REVIEW_TAB2_FRAGMENT(1, "트레이너 리뷰"),
}

enum class MY_PICK_TAB_NAME(var num: Int, var str: String){

    MY_PICK_TAB1_FRAGMENT(0, "트레이너 찜"),
    MY_PICK_TAB2_FRAGMENT(1, "체육시설 찜"),
}