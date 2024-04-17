// 사용자
data class User(

    var userId: String = "",  // 사용자 고유 식별자 ID

    var name: String = "", // 사용자 이름
    var nickname: String = "",  // 사용자의 닉네임
    var email: String = "", // 사용자 이메일
    var gender: String = "",  // 성별
    var age: Int = 0,  // 나이
    var phoneNumber: String = "",  // 전화번호
    var profileImage: String = "",  // 프로필 사진
    var location: String = "",  // 사용자의 위치 정보

    var notificationIdList: MutableList<String> = mutableListOf(),  // 알림 목록
    var recentlyViewedFacilityList: MutableList<String> = mutableListOf(),  // 최근 본 시설 목록
    var recentSearchList: MutableList<String> = mutableListOf(),  // 최근 검색어 목록
    var addressList: MutableList<String> = mutableListOf(),  // 설정한 주소 목록

    var couponIdList: MutableList<String> = mutableListOf(),  // 쿠폰 고유 식별자 ID 목록
    var chatRoomIdList: MutableList<String> = mutableListOf(),  // 채팅방 고유 식별자 ID 목록
    var wishlist: MutableList<Any> = mutableListOf(),  // 찜 목록
    var cart: MutableList<Any> = mutableListOf(),  // 장바구니 목록
    var paymentIdList: MutableList<String> = mutableListOf(),  // 결제 고유 식별자 ID 목록

    var postedCommunityIdList: MutableList<String> = mutableListOf(),  // 작성한 커뮤니티 게시글 고유 식별자 ID 목록
    var postedCommentIdList: MutableList<String> = mutableListOf(),  // 작성한 댓글 고유 식별자 ID 목록
    var likedPostIdList: MutableList<String> = mutableListOf(),  // 추천한 게시글 고유 식별자 ID 목록

    var postedMembershipIdList: MutableList<String> = mutableListOf(),  // 작성한 양도회원권 게시글 고유 식별자 ID 목록 - TransferMembershipPost 정보

    var membershipIdList: MutableList<Membership> = mutableListOf(),  // 가지고 있는 회원권 고유 식별자 ID 목록 - Membership 정보

    var postedReviewIdList: MutableList<String> = mutableListOf(),  // 작성한 리뷰 고유 식별자 ID 목록 - 리뷰 정보

    var point: Int = 0,  // 사용자가 보유하고 있는 포인트
    var pointList: MutableList<Any> = mutableListOf(),  // 포인트 사용 목록

    var status: String = ""  // 유저 상태
)