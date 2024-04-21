package kr.co.lion.finalproject_shoppingmallservice_team1.model

data class Payment(
    val paymentId: String,  // 결제 고유 식별자 ID
    val userId: String,  // 사용자 고유 식별자 ID
    val membershipId: String,  // 회원권 고유 식별자 ID
    val amount: Int,  // 결제 금액
    val method: String,  // 결제 방법
    var status: String  // 결제 상태
)