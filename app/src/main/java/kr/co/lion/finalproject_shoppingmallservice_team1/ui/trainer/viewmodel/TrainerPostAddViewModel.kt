package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lion.finalproject_shoppingmallservice_team1.model.TrainerPost

class TrainerPostAddViewModel: ViewModel() {

    // 트레이너 이름
    val textEditTrainerName = MutableLiveData<String>()

    // 운동센터 이름
    val textEditCenterName = MutableLiveData<String>()

    // 운동센터 위치
    val textEditCenterLocation = MutableLiveData<String>()

    // 운동 타입
    val textEditTrainerType = MutableLiveData<String>()

    // 게시판 상태
    val textEditPostStatus = MutableLiveData<Int>()

    // 트레이너 게시판 모델 객체
    val tPost = TrainerPost()

    init {
        textEditTrainerName.value = ""
        textEditCenterName.value = ""
        textEditCenterLocation.value = ""
        textEditPostStatus.value = 0
        textEditTrainerType.value = ""
        tPost.centerImageUrls.add("센터의 첫번째 사진")
        tPost.trainerProfileImageUrl = ""
        tPost.aboutMePhotosUrls?.add("트레이너 첫번째 자격증사진")
        tPost.trainerPostTopImage.add("트레이너 게시판 첫번째 상단사진")
        tPost.memberShipIdList.add("첫번째 임시 회원권ID")
        tPost.photosUrls.add("첫번째 활동한 트레이너 사진")
        tPost.reviewIdList.add("첫번쨰 리뷰 목록")
    }

}