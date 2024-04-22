package kr.co.lion.finalproject_shoppingmallservice_team1.dao

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.model.CommunityPost
import kr.co.lion.finalproject_shoppingmallservice_team1.model.TrainerPost
import kr.co.lion.finalproject_shoppingmallservice_team1.model.VisitConsulting

class CommunityDao {
    companion object{
        // 순서 값 가져오기
        suspend fun getSequence(): Int {
            var sequenceCommunity = -1

            CoroutineScope(Dispatchers.IO).launch{
                sequenceCommunity =
                    FirebaseFirestore.getInstance().collection("Sequence")
                        .document("SequenceCommunity")
                        .get().await()
                        .getLong("value")?.toInt()!!

            }.join()
            return sequenceCommunity

        }

        // 순서 값 업데이트
        suspend fun updateSequence(sequenceCommunity:Int) {
            CoroutineScope(Dispatchers.IO).launch {
                val documentReference =
                    FirebaseFirestore.getInstance().collection("Sequence")
                        .document("SequenceCommunity")
                val map = mutableMapOf<String, Long>()
                map["value"] = sequenceCommunity.toLong()

                documentReference.set(map)
            }.join()
        }

        // Firebase Database에 모델 값 추가
        suspend fun insertPost(communityPost: CommunityPost) {
            CoroutineScope(Dispatchers.IO).launch {
                FirebaseFirestore.getInstance().collection("CommunityPost")
                    .add(communityPost)
            }.join()
        }
        suspend fun getCommunityList():MutableList<CommunityPost>{
            val dataList = mutableListOf<CommunityPost>()

            CoroutineScope(Dispatchers.IO).launch {
                val query = FirebaseFirestore.getInstance().collection("CommunityPost")
                    .orderBy("communityPostId", Query.Direction.DESCENDING)

                val querySnapshot = query.get().await()

                querySnapshot.forEach {
                    val communityPost= it.toObject(CommunityPost::class.java)
                    dataList.add(communityPost)
                }
            }.join()

            return dataList
        }
    }
}