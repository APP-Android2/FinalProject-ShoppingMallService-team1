package kr.co.lion.finalproject_shoppingmallservice_team1.dao

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.model.Search
import kr.co.lion.finalproject_shoppingmallservice_team1.model.VisitConsulting

class SearchDao {
    companion object{
        // 순서 값 가져오기
        suspend fun getSequence(): Int {
            var sequenceSearch = -1

            CoroutineScope(Dispatchers.IO).launch{
                sequenceSearch =
                    FirebaseFirestore.getInstance().collection("SequenceSearch")
                        .document("Data")
                        .get().await()
                        .getLong("value")?.toInt()!!

            }.join()
            return sequenceSearch

        }

        // 순서 값 업데이트
        suspend fun updateSequence(sequenceSearch:Int) {
            CoroutineScope(Dispatchers.IO).launch {
                val documentReference =
                    FirebaseFirestore.getInstance().collection("SequenceSearch")
                        .document("Data")

                val map = mutableMapOf<String, Long>()
                map["value"] = sequenceSearch.toLong()

                documentReference.set(map)
            }.join()
        }

        // Firebase Database에 모델 값 추가
        suspend fun insertRecentSearch(search:Search) {
            CoroutineScope(Dispatchers.IO).launch {
                FirebaseFirestore.getInstance().collection("Search")
                    .add(search)
            }.join()
        }
        suspend fun getSearchList():MutableList<Search>{
            val dataList = mutableListOf<Search>()

            CoroutineScope(Dispatchers.IO).launch {
                val query = FirebaseFirestore.getInstance().collection("Search")
                    .orderBy("searchIdx", Query.Direction.DESCENDING)

                val querySnapshot = query.get().await()

                querySnapshot.forEach {
                    val search = it.toObject(Search::class.java)
                    dataList.add(search)
                }

            }.join()

            return dataList
        }

    }
}