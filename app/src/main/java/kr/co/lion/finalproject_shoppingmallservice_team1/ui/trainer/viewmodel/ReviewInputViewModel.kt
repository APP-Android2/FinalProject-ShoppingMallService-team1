package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewInputViewModel: ViewModel() {

    // 사용자가 선택한 별점 개수
    val starsNumber = MutableLiveData<Int>()
    // 사용자가 입력한 리뷰 내용
    val reviewText = MutableLiveData<String>()

    init {
        starsNumber.value = 0
        reviewText.value = ""
    }

}