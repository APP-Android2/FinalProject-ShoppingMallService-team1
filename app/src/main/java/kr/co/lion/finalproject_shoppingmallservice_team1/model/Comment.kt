package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 댓글 / 대댓글
class Comment{

    var commentId: String = "" // 댓글의 고유 식별자 ID
    var parentCommentId: String? = null // 대댓글인 경우 부모 댓글의 고유 식별자 ID. 최상위 댓글인 경우 이 필드는 null

    var userId: String = "" // 작성자 고유 식별자 ID - 닉네임, 프로필 사진

    var postTime: String = ""  // 게시글 작성 시간

    var content: String = "" // 댓글 내용

    var commentCount: Int = 0 // 댓글에 달린 댓글 수
    var likes: Int = 0 // 댓글의 좋아요 수
    var reportCount: Int  = 0// 댓글이 신고당한 횟수

    var likedCommentUserIdList: MutableList<String> = mutableListOf() // 좋아요한 유저 고유 식별자 ID 목록

    var status: String = ""  // 댓글 상태
}