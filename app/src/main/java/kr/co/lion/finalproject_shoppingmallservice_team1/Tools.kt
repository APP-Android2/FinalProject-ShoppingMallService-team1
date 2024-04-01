package kr.co.lion.finalproject_shoppingmallservice_team1

class Tools {
    companion object{

    }
}



enum class Navigation_FRAGMENT_NAME(var str:String){

    HOME_FRAGMENT("홈"),
    CENTER_FRAGMENT( "운동 센터"),
    TRAINER_FRAGMENT( "TrainerFragment"),
    COMMUNITY_FRAGMENT( "커뮤니티"),
    MY_FRAGMENT("MY"),
    READ_TRAINER_FRAGMENT("ReadTrainerFragment")
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