package kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyCouponViewModel:ViewModel() {
    val tvRowMyCouponTitle = MutableLiveData<String>()
    val tvRowMyCouponUntilDays = MutableLiveData<String>()
    val tvRowMyCouponSale = MutableLiveData<String>()
}