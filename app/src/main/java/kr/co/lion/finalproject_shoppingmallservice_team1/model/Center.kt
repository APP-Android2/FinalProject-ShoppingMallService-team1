package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 운동 센터
interface FitnessCenter {
    var centerId: String  // 운동 센터 고유 식별자 ID
    var centerName: String  // 운동 센터 이름
    var centerLocation: String  // 운동 센터 위치
    var centerImageUrls: MutableList<String>  // 운동 센터 사진 목록
}

// 운동 센터 게시글
data class FitnessCenterPost (

    override var centerId: String = "",  // 운동 센터 고유 식별자 ID
    override var centerName: String = "",  // 운동 센터 이름
    override var centerLocation: String = "",  // 운동 센터 위치
    override var centerImageUrls: MutableList<String> = mutableListOf(),  // 운동 센터 사진 목록

    var notification: String = "",  // 공지사항
    var introduction: String = "",  // 운동 센터 소개말
    var trainerImageUrlList: MutableList<String> = mutableListOf(),  // 트레이너 사진 목록
    var trainerInfo: String = "",  // 트레이너 간단한 소개

    var openTime: String = "",  // 영업시간 (오픈)
    var closeTime: String = "",  // 영업시간 (마감)

    var freeService: MutableList<String> = mutableListOf(),  // 부가 서비스 (무료)
    var paidService: MutableList<String> = mutableListOf(),  // 부가 서비스 (유료)
    var convenience: MutableList<String> = mutableListOf(),  // 편의시설

    var sns: MutableList<String> = mutableListOf(),  // SNS 바로가기 (링크)

    var reviewIdList: MutableList<String> = mutableListOf(),  // 리뷰 목록
    var reviewCount: Int = 0,  // 작성된 리뷰의 총 개수
    var reviewAvg: Double = 0.0,  // 리뷰 평균 별점
) : FitnessCenter