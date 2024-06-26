package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 방문 상담 신청
data class VisitConsulting (

    var visitConsultingId:Int = 0, // 상담 신청 내역 아이디

    var centerId:String = "", // 운동센터 아이디
    var trainerId:String? = "", // 트레이너 아이디

    var name:String = "", // 이름
    var exercisePurpose:String = "", // 운동 목적
    var applicationDate:String = "", // 희망 신청 날짜
    var applicationTime: String = "",

    var etcContent:String? = "", // 기타

    var stateCheck:Boolean = true // 신청 상태(true-상담 예정 / false-상담 종료)
)