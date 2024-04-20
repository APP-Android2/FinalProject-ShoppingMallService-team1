package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 트레이너
interface Trainer{
    val trainerId: Int                               // 트레이너 ID
    var trainerName: String                          // 트레이너 이름
    var trainerProfileImageUrl: String               // 트레이너 프로필 사진
    var aboutMePhotosUrls: MutableList<String>?      // 트레이너 자격증 사진
}

// 트레이너 화면 게시글
data class TrainerPost(

    // 운동센터
    override var centerId: String = "",                                  // 운동 센터 고유 식별자 ID
    override var centerName: String = "",                                // 운동 센터 이름
    override var centerLocation: String = "",                            // 운동 센터 위치
    override var centerImageUrls: MutableList<String> = mutableListOf(), // 운동 센터 사진 목록

    // 트레이너
    override val trainerId: Int = 0,                                            // 트레이너 고유 식별자 ID
    override var trainerName: String = "",                                      // 트레이너 이름
    override var trainerProfileImageUrl: String = "",                           // 트레이너 프로필 사진 목록
    override var aboutMePhotosUrls: MutableList<String>? = mutableListOf(),     // 트레이너 자격증 사진 목록

    val trainerPostId: Int = 0,                                          // 트레이너 게시글의 ID
    var trainerPostTopImage:  MutableList<String> = mutableListOf(),     // 트레이너 게시글의 상단 사진
    var trainerType: String = "",                                        // 운동 타입(헬스, 필라테스)
    var postStatus: Int = 0,                                             // 게시글 상태 (0: 활성, 1: 삭제)
    var createDate: String = "",                                         // 게시글 생성날짜
    var modifyDate: String = "",                                         // 게시글 수정날짜

    var notificationText: String = "",                                   // 공지사항의 내용
    var aboutMeText: String = "",                                        // 트레이너 소개 내용
    var memberShipText: String = "",                                     // PT의 대한 회원권 설명
    var memberShipIdList: MutableList<String> = mutableListOf(),         // 판매할 회원권 고유 식별자 ID 목록 (회원권 정보)
    var careerText: String = "",                                         // 경력사항 내용
    var orgData: String = "",                                            // 트레이너 소속 운동센터 바로가기 (화면 전환)
    var photosUrls:  MutableList<String> = mutableListOf(),              // 트레이너가 등록한 이미지 URL

    var reviewIdList: MutableList<String> = mutableListOf(),             // 리뷰 목록
    var reviewCount: Int = 0,                                            // 작성된 리뷰의 총 개수
    var reviewAvg: Double = 0.0                                          // 리뷰 평균 별점

): Trainer, FitnessCenter