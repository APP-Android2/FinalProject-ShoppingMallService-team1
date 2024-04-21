package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.UserDao
import kr.co.lion.finalproject_shoppingmallservice_team1.model.User

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

    init {
        myProfilePicture.value = ""
        myProfileName.value = ""
        myProfileNickName.value = ""
        myProfilePhoneNumber.value = ""
        myProfileLocation.value = ""
    }

    fun getUserData(uid:String) = viewModelScope.launch {

        val user = UserDao.getUser(uid)
        user?.let {
            myProfileName.value = it.name
            myProfileNickName.value = it.nickName
            myProfilePhoneNumber.value = it.phoneNumber
            myProfileLocation.value = it.location
        }
    }

    fun updateUserData(uid: String) = viewModelScope.launch{

        val user = User(
            name = myProfileName.value.orEmpty(),
            nickName = myProfileNickName.value.orEmpty(),
            phoneNumber = myProfilePhoneNumber.value.orEmpty(),
            location = myProfileLocation.value.orEmpty(),
            )

        UserDao.updateUser(uid, user)
    }
}