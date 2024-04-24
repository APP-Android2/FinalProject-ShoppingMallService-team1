package kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.FirebaseAuthHelper
import kr.co.lion.finalproject_shoppingmallservice_team1.model.TransferMembershipPost

class TransferMembershipContentListViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<TransferMembershipPost>>()
    val posts: LiveData<List<TransferMembershipPost>> = _posts

    fun loadPosts() = viewModelScope.launch {

        try {
            val postList = mutableListOf<TransferMembershipPost>()
            val db = FirebaseFirestore.getInstance()
            var query = db.collection("TransferMembershipPost")
            if (chipChecked.value == true) {
                query = query.whereEqualTo("userId", FirebaseAuthHelper.getCurrentUser()?.uid) as CollectionReference
            }
            if (filter.value != "모든 운동") {
                query = query.whereEqualTo("exercise", filter.value) as CollectionReference
            }
            val documents = query.get().await()
            Log.d("test1234", "post 목록 가져오기 성공")
            for (doc in documents) {
                val post = doc.toObject<TransferMembershipPost>()
                postList.add(post)
            }
            _posts.value = postList
        } catch (e: Exception) {
            Log.e("test1234", "post 목록 가져오기 실패", e)
            // 필요에 따라 사용자에게 오류 메시지를 표시합니다.
        }
    }

    val chipChecked = MutableLiveData<Boolean>()

    val filter = MutableLiveData<String>()

    init {
        chipChecked.value = false
        filter.value = "모든 운동" // 초기 필터 설정
    }

    fun onChipClicked(view: View) {
        val chip = view as Chip
        chipChecked.value = chip.isChecked

        loadPosts()
    }
}