package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.auth.User

class MyProfileViewModel : ViewModel() {
    // 프로필 사진
    val myProfilePicture = MutableLiveData<String>()
    // 이름
    val myProfileName = MutableLiveData<String>()
    // 닉네임
    val myProfileNickName = MutableLiveData<String>()
    // 휴대폰 번호
    val myProfilePhoneNumber = MutableLiveData<String>()
    // 관심 지역
    val myProfileLocation = MutableLiveData<String>()
}