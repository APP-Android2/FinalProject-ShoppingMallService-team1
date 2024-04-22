package kr.co.lion.finalproject_shoppingmallservice_team1.ui.community.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.CommunityDao
import kr.co.lion.finalproject_shoppingmallservice_team1.model.CommunityPost

class CommunityViewModel:ViewModel() {
    val chipChecked = MutableLiveData<Boolean>()
    val editTextCommunityTitle = MutableLiveData<String>()
    val editTextCommunityContent = MutableLiveData<String>()

    init {
        chipChecked.value = false
    }

    fun onChipClicked(view: View) {
        val chip = view as Chip
        chipChecked.value = chip.isChecked
    }

    fun updateData(){
        CoroutineScope(Dispatchers.Main).launch {
            val sequence = CommunityDao.getSequence()

            CommunityDao.updateSequence(sequence + 1)

            val communityPostId = sequence + 1
            val userId = ""
            val title = editTextCommunityTitle.value!!
            val content = editTextCommunityContent.value!!
            val imageUrls = ""
            val location = ""
            val postTime = ""

            val communityPost = CommunityPost(communityPostId, userId, title, content,
                imageUrls, location, postTime)

            CommunityDao.insertPost(communityPost)
            Log.d("test1234", "CommunityWrite Data")
        }
    }
}