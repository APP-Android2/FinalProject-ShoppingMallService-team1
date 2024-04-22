package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.FirebaseAuthHelper
import kr.co.lion.finalproject_shoppingmallservice_team1.model.User

class MyViewModel : ViewModel() {

    private val _profileData = MutableLiveData<User?>(null)
    val profileData: LiveData<User?> get() = _profileData

    init {
        viewModelScope.launch {
            val userId = FirebaseAuthHelper.getCurrentUser()?.uid
            val docRef = FirebaseFirestore.getInstance().collection("users").document(userId!!)
            docRef.addSnapshotListener { snapshot, error ->
                if (error != null){
                    // 에러
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()){
                    // 데이터가 존재하면 StateFlow 업데이트
                    _profileData.value = snapshot.toObject<User>()
                }
            }
        }
    }
}