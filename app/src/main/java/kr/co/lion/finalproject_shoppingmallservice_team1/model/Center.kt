package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 운동 센터 기본 정보
interface Center {
    var centerId: String    // 운동 센터 ID
    var centerName: String    // 운동 센터 이름
    var centerLocation: String    // 운동 센터 위치
    var centerImageUrl: List<Any>  // 운동 센터 사진
}

// 운동 센터 정보탭
class CenterInfo : Center {

    override var centerId: String = ""  // 운동 센터 ID
    override var centerName: String = ""    // 운동 센터 이름
    override var centerLocation: String = ""    // 운동 센터 주소
    override var centerImageUrl: List<Any> = mutableListOf()    // 운동 센터 사진

    var notification: String = ""   // 공지사항
    var introduction: String = ""   // 운동 센터 소개말
    var trainerImage: List<Any> = mutableListOf()    // 트레이너 사진
    var trainerInfo: List<Any> = mutableListOf()    // 트레이너 간단한 소개
    var openTime: String = ""   // 운영시간
    var freeService: String = ""    // 부가 서비스 (무료)
    var paidService: String = ""    // 부가 서비스 (유료)
    var convenience: String = ""    // 편의시설
    var sns: List<Any> = mutableListOf()    // SNS 바로가기
}

// 운동 센터 상담탭
class CenterConsulting : Center {
    override var centerId: String = ""  // 운동 센터 ID
    override var centerName: String = ""  // 운동 센터 이름
    override var centerLocation: String = ""  // 운동 센터 위치
    override var centerImageUrl: List<Any> = mutableListOf()   // 운동 센터 사진

    // 방문 상담
    var userName: String = ""   // 이름
    var visitTime: String = ""  // 방문 신청 시간
    var userReason: String = "" // 운동 목적
    var other: String = ""  // 기타

    // 채팅 상담
    var trainerName: String = ""    // 트레이너 이름
    var chatTime: String = ""   // 채팅 시간

}