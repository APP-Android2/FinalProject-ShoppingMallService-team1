// 양도회원권 게시글
class TransferMembershipPost {

    val transferMembershipPostId: String = ""  // 게시글 고유 식별자 ID

    var userId: String = "" // 작성자 고유 식별자 ID - 작성자 닉네임, 작성자 프로필 이미지

    var postTime: String = ""  // 게시글 작성 시간

    var membershipId: String = ""  // 회원권 고유 식별자 ID - 회원권의 모든 정보

    var price: Int = 0 // 판매 금액
    var status: String = ""  // 게시글 상태
}