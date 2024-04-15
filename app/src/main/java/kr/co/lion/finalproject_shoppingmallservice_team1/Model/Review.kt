package kr.co.lion.finalproject_shoppingmallservice_team1.Model

// 리뷰
interface Review {

    var reviewId: String // 리뷰 ID
    var reviewStatus: Int // 리뷰의 상태 (0: 활성, 1: 삭제)
    var createDate: String // 리뷰 생성날짜
    var modifyDate: String // 리뷰 수정날짜
    var starsNumber: Int // 사용자가 선택한 별점 개수
    var reviewText: String // 리뷰 내용
    var reviewImageUrls: List<String> // 리뷰에 첨부된 이미지 URL
}

// 트레이너 화면의 리뷰
class TrainerReview : Review {

    override var reviewId: String = "" // 리뷰 ID
    override var reviewStatus: Int = 0 // 리뷰의 상태 (0: 활성, 1: 삭제)
    override var createDate: String = "" // 리뷰 생성날짜
    override var modifyDate: String = "" // 리뷰 수정날짜
    override var starsNumber: Int = 0 // 사용자 별점 개수
    override var reviewText: String = "" // 리뷰 내용
    override var reviewImageUrls: List<String> = mutableListOf() // 리뷰 이미지

    var nickname: String = "" // 사용자 닉네임 가져오기
    var TrainerName: String = "" // 트레이너 이름 가져오기
    var memberShipName: String = "" // 회원권 이름 가져오기
}

// 운동센터 화면의 리뷰
class CenterReview : Review {

    override var reviewId: String = "" // 리뷰 ID
    override var reviewStatus: Int = 0 // 리뷰의 상태 (0: 활성, 1: 삭제)
    override var createDate: String = "" // 리뷰 생성날짜
    override var modifyDate: String = "" // 리뷰 수정날짜
    override var starsNumber: Int = 0 // 사용자 별점 개수
    override var reviewText: String = "" // 리뷰 내용
    override var reviewImageUrls: List<String> = mutableListOf() // 리뷰 이미지

    var nickname: String = "" // 사용자 닉네임 가져오기
    var CenterName: String = "" // 운동센터 이름 가져오기
    var memberShipName: String = "" // 회원권 이름 가져오기
}

// 리뷰 댓글 class
class ReviewComment {

    var commentId: String = "" // 댓글 ID
    var reviewParentsId: String = "" // 부모 리뷰 ID
    var commentStatus: Int = 0 // 댓글의 상태 (0: 활성, 1: 삭제)
    var createDate: String = "" // 댓글 생성날짜
    var commentText: String = "" // 댓글 내용

    var name: String = "" // 운동센터 또는 트레이너 이름 가져오기 (작성자)
}