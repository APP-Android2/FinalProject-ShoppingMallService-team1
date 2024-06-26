package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 회원권
interface Membership {
    val membershipId: String  // 회원권 고유 식별자 ID
    var name: String  // 회원권 이름
    var centerId: String // 운동 센터 Id - 운동 센터 이름, 운동 센터 위치
    var membershipType: MembershipType // 회원권 타입
    var exerciseType: ExerciseType // 운동 타입
    var price: Int  // 회원권 가격
    //var status: MembershipStatus // 회원권 상태
    var status: Int // 회원권 상태
}

// 운동 센터 회원권
data class FitnessCenterMembership(
    override val membershipId: String = "",  // 회원권 고유 식별자 ID
    override var name: String = "",  // 회원권 이름
    override var centerId: String = "", // 운동 센터 고유 식별자 ID - 운동 센터 이름, 운동 센터 위치
    override var membershipType: MembershipType = MembershipType.FITNESS_CENTER, // 타입 운동 센터
    override var exerciseType: ExerciseType = ExerciseType.HEALTH, // 헬스
    var duration: String = "",  // 회원권 기간
    var startDate: String = "",  // 시작 날짜
    var endDate: String = "",  // 끝나는 날짜
    override var price: Int = 0,  // 회원권 가격
    override var status: Int = MembershipStatus.ACTIVE.num, // 회원권 상태
) : Membership

// PT 회원권
data class PTMembership(
    override val membershipId: String = "",  // 회원권 고유 식별자 ID
    override var name: String = "",  // 회원권 이름
    override var centerId: String = "", // 운동 센터 고유 식별자 ID - 운동 센터 이름, 운동 센터 위치
    override var membershipType: MembershipType = MembershipType.PT, // 타입 PT
    override var exerciseType: ExerciseType = ExerciseType.HEALTH, // 헬스
    var trainerId: String = "",  // 트레이너 Id - 트레이너 이름
    override var price: Int = 0,  // 회원권 가격
    var pricePerSession: Int = 0,  // 회당 가격
    var count: Int = 0,  // PT 세션 수
    var trainerPostId: Int = 0,    // 트레이너 게시판 고유 식별자 ID
    override var status: Int = MembershipStatus.ACTIVE.num // 회원권 상태
) : Membership

enum class MembershipType(var str:String) {
    FITNESS_CENTER("운동 센터"),     // FitnessCenter
    PT("PT"),   // PT
}

enum class ExerciseType(var str:String) {
    HEALTH("헬스"), // 헬스
    PILATES("필라테스"), // 필라테스
    SWIMMING("수영"), // 수영
}

enum class MembershipStatus(var str:String, var num:Int) {
    ACTIVE("활성화", 0),     // 활성 상태
    INACTIVE("비활성화", 1),   // 비활성 상태
    EXPIRATION("만료", 2)   // 만료 상태
}