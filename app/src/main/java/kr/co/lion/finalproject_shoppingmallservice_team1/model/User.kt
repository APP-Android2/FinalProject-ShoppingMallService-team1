package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 사용자
data class User(

    var id: String = "",  // 사용자 UID

    var name: String = "", // 사용자 이름
    var nickname: String = "",  // 사용자의 닉네임
    var email: String = "", // 사용자 이메일
    var gender: String = "",  // 성별
    var age: Int = 0,  // 나이
    var phoneNumber: String = "",  // 전화번호
    var profileImage: String = "",  // 프로필 사진
    var location: String = "",  // 사용자의 위치 정보

    var notificationList: MutableList<Any> = mutableListOf(),  // 알림 목록
    var recentlyViewedFacilityList: MutableList<Any> = mutableListOf(),  // 최근 본 시설 목록
    var recentSearchList: MutableList<Any> = mutableListOf(),  // 최근 검색어 목록
    var addressList: MutableList<Any> = mutableListOf(),  // 설정한 주소 목록
    var couponList: MutableList<Any> = mutableListOf(),  // 쿠폰 목록
    var chatList: MutableList<Any> = mutableListOf(),  // 채팅 목록
    var wishlist: MutableList<Any> = mutableListOf(),  // 찜 목록
    var cart: MutableList<Any> = mutableListOf(),  // 장바구니 목록
    var postedCommunityList: MutableList<Any> = mutableListOf(),  // 작성한 커뮤니티 게시글 목록
    var postedCommentList: MutableList<Any> = mutableListOf(),  // 작성한 댓글 목록
    var recommendedPostList: MutableList<Any> = mutableListOf(),  // 추천한 게시글 목록
    var postedMembershipList: MutableList<Any> = mutableListOf(),  // 작성한 양도회원권 게시글 목록
    var purchasedMembershipList: MutableList<Membership> = mutableListOf(),  // 구매한 회원권 결제 목록
    var activeMembershipList: MutableList<Membership> = mutableListOf(),  // 사용 중인 회원권 목록
    var expiredMembershipList: MutableList<Membership> = mutableListOf(),  // 기간이 만료한 회원권 목록
    var postedReviewList: MutableList<Any> = mutableListOf(),  // 작성한 리뷰 목록
    var point: Int = 0,  // 사용자가 보유하고 있는 포인트
    var pointList: MutableList<Any> = mutableListOf(),  // 포인트 사용 목록
    var status: String = ""  // 유저 상태
)