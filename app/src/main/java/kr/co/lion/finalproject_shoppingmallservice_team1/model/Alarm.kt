package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 알림
data class Alarm (
    val alarmId:Int = 0, // 알림 아이디

    val title:String = "", // 알림 제목
    val date:String = ""  // 알림 업로드 날짜
)