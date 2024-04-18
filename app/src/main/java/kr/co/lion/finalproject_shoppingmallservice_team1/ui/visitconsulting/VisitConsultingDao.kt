package kr.co.lion.finalproject_shoppingmallservice_team1.ui.visitconsulting

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kr.co.lion.finalproject_shoppingmallservice_team1.model.VisitConsulting

class VisitConsultingDao {
    companion object {

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

        suspend fun insertApplication(visitConsulting: VisitConsulting) {
            CoroutineScope(Dispatchers.IO).launch {
                FirebaseFirestore.getInstance().collection("VisitConsulting")
                    .add(visitConsulting)
            }.join()
        }

        suspend fun getVisitData(){

        }

        suspend fun getVisitList(){

        }
    }
}