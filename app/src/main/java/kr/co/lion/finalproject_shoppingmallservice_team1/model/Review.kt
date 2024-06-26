package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 리뷰
interface Review {

    var reviewId: Int                        // 리뷰 고유 식별자 ID
    var reviewStatus: Int                    // 리뷰의 상태 (0: 활성, 1: 삭제)
    var createDate: String                   // 리뷰 생성날짜
    var modifyDate: String                   // 리뷰 수정날짜
    var starsNumber: Int                     // 사용자가 선택한 별점 개수
    var reviewText: String                   // 리뷰 내용
    var reviewImageUrls: String              // 리뷰에 첨부된 이미지 URL (추후 타입 변경)
    var userId: String                       // 작성자 고유 식별자 ID
}

// 트레이너 화면의 리뷰
data class TrainerReview(

    // 리뷰
    override var reviewId: Int = 0,                                       // 리뷰 고유 식별자 ID
    override var reviewStatus: Int = 0,                                   // 리뷰의 상태 (0: 활성, 1: 삭제)
    override var createDate: String = "",                                 // 리뷰 생성날짜
    override var modifyDate: String = "",                                 // 리뷰 수정날짜
    override var starsNumber: Int = 0,                                    // 사용자가 선택한 별점 개수
    override var reviewText: String = "",                                 // 리뷰 내용
    override var reviewImageUrls: String = "",                            // 리뷰에 첨부된 이미지 URL 목록
    override var userId: String = "",                                     // 작성자 고유 식별자 ID

    // 트레이너
    override val trainerId: Int = 0,                                            // 트레이너 고유 식별자 ID
    override var trainerName: String = "",                                      // 트레이너 이름
    override var trainerProfileImageUrl: String = "",                           // 트레이너 프로필 사진 목록
    override var aboutMePhotosUrls: MutableList<String>? = mutableListOf(),     // 트레이너 자격증 사진 목록

    var trainerPostId: Int = 0,                                           // 트레이너 게시글 고유 식별자 ID - 트레이너 게시글 정보
    var membershipId: String = "",                                        // 회원권 고유 식별자 ID - 회원권 정보
    var reviewCommentId: String = ""                                      // 리뷰 댓글 - 댓글은 하나만 달수 있으니 list 사용 x

) : Review, Trainer


// 운동센터 화면의 리뷰
data class FitnessCenterReview(

    override var reviewId: Int = 0,                                       // 리뷰 고유 식별자 ID
    override var reviewStatus: Int = 0,                                   // 리뷰의 상태 (0: 활성, 1: 삭제)
    override var createDate: String = "",                                 // 리뷰 생성날짜
    override var modifyDate: String = "",                                 // 리뷰 수정날짜
    override var starsNumber: Int = 0,                                    // 사용자가 선택한 별점 개수
    override var reviewText: String = "",                                 // 리뷰 내용
    override var reviewImageUrls: String = "",                            // 리뷰에 첨부된 이미지 URL 목록
    override var userId: String = "",                                     // 작성자 고유 식별자 ID

    var fitnessCenterPostId: Int = 0,                                     // 운동 센터 게시글 고유 식별자 ID (운동 센터 게시글 정보)
    var membershipId: String = "",                                        // 회원권 고유 식별자 ID (회원권 정보)
    var reviewCommentId: String = ""                                      // 리뷰 댓글 - 댓글은 하나만 달수 있으니 list 사용 x

) : Review


// 리뷰 댓글 class
data class ReviewComment(

    var commentId: String = "",           // 댓글 고유 식별자 ID
    var reviewParentsId: String = "",     // 부모 리뷰의 고유 식별자 ID
    var commentStatus: Int = 0,           // 댓글의 상태 (0: 활성, 1: 삭제)
    var commentCreateDate: String = "",   // 댓글 생성날짜
    var commentText: String = "",         // 댓글 내용
    var userId:String = ""                // 작성자 고유 식별자 ID
)