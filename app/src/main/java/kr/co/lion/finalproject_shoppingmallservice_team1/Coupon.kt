package kr.co.lion.finalproject_shoppingmallservice_team1

// 쿠폰
class Coupon {
    var id:String = "" // 쿠폰 ID

    var name:String = "" // 쿠폰 이름
    var content:String = "" // 할인이 적용되는 내용
    var discountRate:Int = 0 // 쿠폰의 할인율

    var startDate:String = "" // 쿠폰을 다운받을 수 있는 첫 시작 날짜
    var lastDate:String = "" // 쿠폰 만료일
    var remainPeriod:Int = 0 // 쿠폰의 남은 기간

    var stateCheck:Boolean = true // 쿠폰 사용 가능 여부(true: 사용 가능 / false: 사용 불가)
}