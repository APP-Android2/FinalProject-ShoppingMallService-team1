package kr.co.lion.finalproject_shoppingmallservice_team1

class Tools {
    companion object{

    }
}

enum class MAIN_FRAGMENT_NAME(var num:Int, var str:String){

    MAIN_LOGO_FRAGMENT(0, "메인 로고"),
    MEMBER_EXPLANATION_FRAGMENT(1, "회원 설명"),
}

enum class Navigation_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_FRAGMENT(0, "홈"),
    CENTER_FRAGMENT(1, "운동 센터"),
    TRAINER_FRAGMENT(2, "트레이너"),
    COMMUNITY_FRAGMENT(3, "커뮤니티"),
    MY_FRAGMENT(4, "MY"),
}

enum class HOME_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_A_FRAGMENT(0, "A"),
    HOME_B_FRAGMENT(1, "B"),
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