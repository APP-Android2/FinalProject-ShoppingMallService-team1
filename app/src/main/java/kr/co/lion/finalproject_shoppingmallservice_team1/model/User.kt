package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 사용자
data class User(
    var userId: String = "",  // 사용자 고유 식별자 ID

    var name: String = "", // 사용자 이름
    var nickName: String = "",  // 사용자의 닉네임
    var email: String = "", // 사용자 이메일
    var gender: String = "",  // 성별
    var age: Int = 0,  // 나이
    var phoneNumber: String = "",  // 전화번호
    var profileImage: String = "",  // 프로필 사진 URL
    var location: String = "",  // 사용자의 위치 정보

    var notificationIds: List<String> = emptyList(),  // 알림 ID 목록
    var recentlyViewedFacilities: List<String> = emptyList(),  // 최근 본 시설 ID 목록
    var recentSearches: List<String> = emptyList(),  // 최근 검색어 목록
    var addresses: List<String> = emptyList(),  // 설정한 주소 목록

    var couponIds: List<String> = emptyList(),  // 쿠폰 ID 목록
    var chatRoomIds: List<String> = emptyList(),  // 채팅방 ID 목록
    var wishlist: List<Any> = emptyList(),  // 찜 목록
    var cart: List<Any> = emptyList(),  // 장바구니 목록
    var paymentIds: List<String> = emptyList(),  // 결제 ID 목록

    var postedCommunityIds: List<String> = emptyList(),  // 작성한 커뮤니티 게시글 ID 목록
    var postedCommentIds: List<String> = emptyList(),  // 작성한 댓글 ID 목록
    var likedPostIds: List<String> = emptyList(),  // 추천한 게시글 ID 목록

    var postedMembershipIds: List<String> = emptyList(),  // 작성한 양도회원권 게시글 ID 목록

    var memberships: List<Membership> = emptyList(),  // 가지고 있는 회원권 목록

    var postedReviewIds: List<String> = emptyList(),  // 작성한 리뷰 ID 목록

    var points: Int = 0,  // 사용자가 보유하고 있는 포인트
    var pointHistory: List<Any> = emptyList(),  // 포인트 거래 내역

    var status: UserStatus = UserStatus.ACTIVE  // 유저 상태
)

// 유저 상태를 나타내는 열거형
enum class UserStatus {
    ACTIVE,     // 활성 상태: 사용자가 활발하게 앱을 사용 중임
    INACTIVE,   // 비활성 상태: 일정 기간 동안 앱을 사용하지 않은 사용자
    SUSPENDED   // 정지 상태: 규정 위반 등의 이유로 계정이 정지된 사용자
}