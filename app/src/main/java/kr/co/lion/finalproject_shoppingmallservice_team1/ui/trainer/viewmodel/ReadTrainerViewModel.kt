package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReadTrainerViewModel: ViewModel() {

    // 트레이너 이름
    val readTrainerNameTextView = MutableLiveData<String>()
    // 트레이너 소속 센터 이름
    val readTrainerOrgNameTextView = MutableLiveData<String>()
    // 트레이너 소속 센터 주소
    val readTrainerLocationTextView = MutableLiveData<String>()
    // 트레이너 프로필 사진
    val trainerProfileImageUrl = MutableLiveData<String>()


    // 공지사항
    val readTrainerNotificationText = MutableLiveData<String>()
    // 트레이너 소개
    val readTrainerAboutMeText = MutableLiveData<String>()
    // 맞춤 PT
    val readTrainerMemberShipText = MutableLiveData<String>()
    // 경력사항
    val readTrainerCareerText = MutableLiveData<String>()
    // 리뷰 평균 및 개수
    val readTrainerReviewAvg = MutableLiveData<Double>()
    val readTrainerReviewCount = MutableLiveData<Int>()

}