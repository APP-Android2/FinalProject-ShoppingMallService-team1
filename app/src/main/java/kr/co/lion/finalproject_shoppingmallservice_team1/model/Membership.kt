// 회원권
interface Membership {
    val membershipId: String  // 회원권 고유 식별자 ID
    var name: String  // 회원권 이름
    var centerId: String // 운동 센터 Id - 운동 센터 이름, 운동 센터 위치
    var price: Int  // 회원권 가격
    var status: String // 회원권 상태
}

// 운동 센터 회원권
data class FitnessCenterMembership(
    override val membershipId: String = "",  // 회원권 고유 식별자 ID
    override var name: String = "",  // 회원권 이름
    override var centerId: String = "", // 운동 센터 고유 식별자 ID - 운동 센터 이름, 운동 센터 위치
    var duration: String = "",  // 회원권 기간
    var startDate: String = "",  // 시작 날짜
    var endDate: String = "",  // 끝나는 날짜
    override var price: Int = 0,  // 회원권 가격
    override var status: String = "" // 회원권 상태
) : Membership

// PT 회원권
data class PTMembership(
    override val membershipId: String = "",  // 회원권 고유 식별자 ID
    override var name: String = "",  // 회원권 이름
    override var centerId: String = "", // 운동 센터 고유 식별자 ID - 운동 센터 이름, 운동 센터 위치
    var trainerId: String = "",  // 트레이너 Id - 트레이너 이름
    override var price: Int = 0,  // 회원권 가격
    var pricePerSession: Int = 0,  // 회당 가격
    var count: Int = 0,  // PT 세션 수
    override var status: String = "" // 회원권 상태
) : Membership
