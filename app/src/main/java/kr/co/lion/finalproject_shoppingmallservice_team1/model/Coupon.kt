package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 쿠폰
data class Coupon (
    var couponId:String = "", // 쿠폰 ID

    var userId:String = "", // 사용자 ID

    var name:String = "", // 쿠폰 이름
    var content:String = "", // 할인이 적용되는 내용
    var discountRate:Int = 0, // 쿠폰의 할인율

    var availability:Boolean = true, // 쿠폰 사용 가능 여부(true: 사용 가능 / false: 사용 불가)

    var startDate:String = "", // 쿠폰을 다운받을 수 있는 첫 시작 날짜
    var endDate:String = "", // 쿠폰 만료일
    var remainingPeriod:Int = 0, // 쿠폰의 남은 기간

    var status:String = "" // 보유한 또는 다운로드 가능한 쿠폰을 User에서 구분
)
