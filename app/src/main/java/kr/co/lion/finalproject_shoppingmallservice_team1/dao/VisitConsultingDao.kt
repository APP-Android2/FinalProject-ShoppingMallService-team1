package kr.co.lion.finalproject_shoppingmallservice_team1.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.model.VisitConsulting

class VisitConsultingDao {
    companion object {

        // 순서 값 가져오기
        suspend fun getSequence(): Int {
            var sequenceVisitConsulting = -1

            CoroutineScope(Dispatchers.IO).launch{
                sequenceVisitConsulting =
                    FirebaseFirestore.getInstance().collection("SequenceVisitConsulting")
                        .document("Data")
                        .get().await()
                        .getLong("value")?.toInt()!!

            }.join()
            return sequenceVisitConsulting

        }

        // 순서 값 업데이트
        suspend fun updateSequence(sequenceVisitConsulting:Int) {
            CoroutineScope(Dispatchers.IO).launch {
                val documentReference =
                    FirebaseFirestore.getInstance().collection("SequenceVisitConsulting")
                        .document("Data")

                val map = mutableMapOf<String, Long>()
                map["value"] = sequenceVisitConsulting.toLong()

                documentReference.set(map)
            }.join()
        }

        // Firebase Database에 모델 값 추가
        suspend fun insertApplication(visitConsulting: VisitConsulting) {
            CoroutineScope(Dispatchers.IO).launch {
                FirebaseFirestore.getInstance().collection("VisitConsulting")
                    .add(visitConsulting)
            }.join()
        }

        // VisitConsulting컬렉션의 data를 리스트로 정렬 후 반환
        suspend fun getVisitList():MutableList<VisitConsulting>{
            val dataList = mutableListOf<VisitConsulting>()

            CoroutineScope(Dispatchers.IO).launch {
                val query = FirebaseFirestore.getInstance().collection("VisitConsulting")
                    .orderBy("visitConsultingId", Query.Direction.DESCENDING)

                val querySnapshot = query.get().await()

                querySnapshot.forEach {
                    val visitConsulting = it.toObject(VisitConsulting::class.java)
                    dataList.add(visitConsulting)
                }
            }.join()

            return dataList
        }
    }
}