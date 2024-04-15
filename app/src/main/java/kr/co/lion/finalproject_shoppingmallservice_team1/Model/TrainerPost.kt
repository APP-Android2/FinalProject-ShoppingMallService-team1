package kr.co.lion.finalproject_shoppingmallservice_team1.Model


// 트레이너 화면 게시글
class TrainerPost {
    var trainerPostId: String = "" // 트레이너 게시글의 ID
    var profilePhoto: String = "" // 트레이너 게시글의 프로필 사진
    var trainerType: String = "" // 운동 타입(헬스, 필라테스)
    var postStatus: Int = 0 // 게시글 상태 (0: 활성, 1: 삭제)
    var createDate: String = "" // 게시글 생성날짜
    var modifyDate: String = "" // 게시글 수정날짜

    var trainerName: String = "" // 트레이너 이름
    var centerName: String = "" // 트레이너 소속의 운동센터 이름
    var centerAddress: String = "" // 운동센터 주소

    var notificationTitle: String = "공지사항" // 공지사항 제목 (고정값)
    var notificationText: String = "" // 공지사항의 내용
    var aboutMeTitle: String = "트레이너 소개" // 트레이너 소개 (고정값)
    var aboutMeText: String = "" // 트레이너 소개 내용
    var aboutMePhotosUrls: List<String> = mutableListOf() // 자격증 이미지 URL
    var memberShipTitle: String = "맞춤PT" // 맞춤PT (고정값)
    var memberShipText: String = "" // PT의 대한 회원권 설명
    var memberShipData: String = "" // 회원권 인터페이스 데이터 가져오기
    var careerTitle: String = "경력사항" // 경력사항 (고정값)
    var careerText: String = "" // 경력사항 내용
    var orgTitle: String = "소속" // 소속 (고정값)
    var orgData: String = "" // 트레이너 소속 운동센터 바로가기 데이터
    var photosTitle: String = "사진" // 사진 (고정값)
    var photosUrls:  List<String> = mutableListOf() // 트레이너가 등록한 이미지 URL

    var reviewCount: Int = 0 // 작성된 리뷰의 총 개수
    var reviewAvg: Double = 0.0 // 리뷰 평균 별점
}