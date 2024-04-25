package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.dao

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.model.PTMembership

class ReadTrainerTabDao {

    companion object{
        // 트레이너 게시판ID를 받아서 해당 게시판의 트레이너 회원권만 가져온다.
        suspend fun gettingTrainerMembershipList(trainerPostId: Int):MutableList<PTMembership>{
            val trainerMembershipList = mutableListOf<PTMembership>()
            Log.d("test123", "Dao: ${trainerPostId}")

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("Memberships")
                // 활성화 String 값을 찾지 못하는 이슈 사항으로 Int 값으로 수정.
                var query = collectionReference.whereEqualTo("status", 0)
                query = query.whereEqualTo("trainerPostId", trainerPostId)
                query = query.orderBy("name", Query.Direction.ASCENDING)

                val queryShapshot = query.get().await()

                // 위에서 가져온 문서의 수 만큼 반복한다.
                queryShapshot.forEach{
                    val ptMembership = it.toObject(PTMembership::class.java)
                    trainerMembershipList.add(ptMembership)
                }
            }
            job1.join()

            return trainerMembershipList
        }
    }
}