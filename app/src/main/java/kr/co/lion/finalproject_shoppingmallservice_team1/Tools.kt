package kr.co.lion.finalproject_shoppingmallservice_team1

class Tools {
    companion object{

    }
}

enum class LOGIN_FRAGMENT_NAME(var num:Int, var str:String){

    LOGIN_FRAGMENT(0, "로그인")
}

enum class NAVIGATION_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_FRAGMENT(0, "홈"),
    CENTER_FRAGMENT(1, "운동 센터"),
    TRAINER_FRAGMENT(2, "트레이너"),
    COMMUNITY_FRAGMENT(3, "커뮤니티"),
    MY_FRAGMENT(4, "MY"),
    READ_TRAINER_FRAGMENT(5, "ReadTrainerFragment"),
    MY_PROFILE_FRAGMENT(6, "MyProfileFragment"),
    MY_NOTIFICATION_FRAGMENT(7, "MyNotificationFragment"),
}

enum class HOME_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_ALARM_FRAGMENT(0, "알림"),
    HOME_CHAT_FRAGMENT(1, "쪽지"),
    HOME_SHOP_FRAGMENT(2, "장바구니")

}
enum class HOME_BOTTOM_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_ADDRESS_BOTTOM_MAP_FRAGMENT(0, "현재 위치")
}

enum class CENTER_FRAGMENT_NAME(var num:Int, var str:String){

    CENTER_A_FRAGMENT(0, "A"),
    CENTER_B_FRAGMENT(1, "B"),
}

enum class TRAINER_FRAGMENT_NAME(var num:Int, var str:String){

    TRAINER_A_FRAGMENT(0, "A"),
    TRAINER_B_FRAGMENT(1, "B"),
}

enum class COMMUNITY_FRAGMENT_NAME(var num:Int, var str:String){

    COMMUNITY_A_FRAGMENT(0, "A"),
    COMMUNITY_B_FRAGMENT(1, "B"),
}

enum class MY_FRAGMENT_NAME(var num:Int, var str:String){

    MY_A_FRAGMENT(0, "A"),
    MY_B_FRAGMENT(1, "B"),
}