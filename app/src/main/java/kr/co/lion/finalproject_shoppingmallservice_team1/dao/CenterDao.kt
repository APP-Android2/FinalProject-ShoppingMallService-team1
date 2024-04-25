package kr.co.lion.finalproject_shoppingmallservice_team1.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.model.Center

class CenterDao {
    companion object{
        suspend fun getCenterList():MutableList<Center>{
            val dataList = mutableListOf<Center>()

            CoroutineScope(Dispatchers.IO).launch {
                val query = FirebaseFirestore.getInstance().collection("Center")
                    .orderBy("centerId", Query.Direction.DESCENDING)

                val querySnapshot = query.get().await()
                querySnapshot.forEach {
                    val center = it.toObject(Center::class.java)
                    dataList.add(center)
                }
            }.join()

            return dataList
        }
    }
}