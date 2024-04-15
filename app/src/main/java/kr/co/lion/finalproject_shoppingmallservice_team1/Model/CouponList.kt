package kr.co.lion.finalproject_shoppingmallservice_team1.Model

// 쿠폰 목록
class CouponList {
    // 전체 쿠폰 목록
    var couponEntireList : MutableList<Coupon> = mutableListOf()

    // 보유한 쿠폰 목록
    var couponOwnList : MutableList<Coupon> = mutableListOf()

    // 다운로드 가능한 쿠폰 목록
    var couponDownList : MutableList<Coupon> = mutableListOf()

}