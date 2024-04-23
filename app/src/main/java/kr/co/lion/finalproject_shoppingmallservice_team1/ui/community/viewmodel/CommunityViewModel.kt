package kr.co.lion.finalproject_shoppingmallservice_team1.ui.community.viewmodel

import CommunityPost
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.FirebaseAuthHelper
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.CommunityDao
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
@RequiresApi(Build.VERSION_CODES.O)
class CommunityViewModel:ViewModel() {
    val chipChecked = MutableLiveData<Boolean>()
    val editTextCommunityTitle = MutableLiveData<String>()
    val editTextCommunityContent = MutableLiveData<String>()

    val zoneId = ZoneId.of("Asia/Seoul")
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    init {
        chipChecked.value = false
    }

    fun onChipClicked(view: View) {
        val chip = view as Chip
        chipChecked.value = chip.isChecked
    }

    fun updateData(){
        CoroutineScope(Dispatchers.Main).launch {

            val postDate = LocalDateTime.now(zoneId)

            val sequence = CommunityDao.getSequence()

            CommunityDao.updateSequence(sequence + 1)

            val communityPostId = sequence + 1
            val userId = FirebaseAuthHelper.getCurrentUser()?.uid
            val title = editTextCommunityTitle.value!!
            val content = editTextCommunityContent.value!!
            val postTime = getPostTime(postDate.format(formatter))
            val location = ""
            val imageUrls = mutableListOf<String>()

            val communityPost = CommunityPost(communityPostId, userId!!, title, content,
                postTime, location, imageUrls)

            CommunityDao.insertPost(communityPost)
        }
    }

    fun getPostTime(postDate:String):String{
        var postTime:String

        val currentDate = LocalDateTime.now(zoneId)
        val postDateTime = LocalDateTime.parse(postDate, formatter)

        val time = ChronoUnit.HOURS.between(postDateTime, currentDate)

        if (time >= 24){
            postTime = postDate
            Log.d("test1234", "date ${postTime}")
            return postTime
        }
        else if (time < 1){
            postTime = "방금 전"
            Log.d("test1234", "date ${postTime}")
            return postTime
        }
        else{
            postTime = "${time}시간 전"
            Log.d("test1234", "date ${postTime}")
            return postTime
        }
    }

}