import android.graphics.Bitmap

// 커뮤니티 게시글
data class CommunityPost (

    var communityPostId: Int = 0, // 게시글의 고유 ID

    var userId: String = "", // 작성자 고유 ID - 닉네임, 프로필 사진

    var title: String = "", // 게시글의 제목
    var content: String = "", // 게시글의 내용

    var postTime: String = "",  // 게시글 작성 시간
    var location: String = "", // 게시글 작성 위치


    var imageUrls: MutableList<String> = mutableListOf(), // 게시글에 첨부된 이미지 URL 목록

    var hashtags: MutableList<String> = mutableListOf(), // 게시글에 포함된 해시태그 목록

    var likes: Int = 0, // 게시글의 좋아요 수
    var viewCount: Int = 0, // 게시글의 조회수
    var commentCount: Int = 0, // 게시글에 달린 댓글 수
    var reportCount: Int = 0, // 게시글이 신고당한 횟수

    var likedUserIdList: MutableList<String> = mutableListOf(), // 좋아요한 유저 고유 식별자 ID 목록
    var commentIdList: MutableList<String> = mutableListOf(), // 게시글에 달린 댓글 고유 식별자 ID 목록

    var status: String = ""  // 게시글 상태
)