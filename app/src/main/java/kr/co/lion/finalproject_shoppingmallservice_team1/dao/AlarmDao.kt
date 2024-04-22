package kr.co.lion.finalproject_shoppingmallservice_team1.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.model.Alarm

class AlarmDao {
    companion object{
        suspend fun getAlarmList():MutableList<Alarm>{
            val dataList = mutableListOf<Alarm>()

            CoroutineScope(Dispatchers.IO).launch {
                val query = FirebaseFirestore.getInstance().collection("Alarm")
                    .orderBy("alarmId", Query.Direction.DESCENDING)

                val querySnapshot = query.get().await()
                querySnapshot.forEach {
                    val alarm = it.toObject(Alarm::class.java)
                    dataList.add(alarm)
                }
            }.join()

            return dataList
        }
    }
}