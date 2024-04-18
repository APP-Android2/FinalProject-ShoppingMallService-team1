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
        var sequenceVisitConsulting = -1
        suspend fun getSequence(): Int = withContext(Dispatchers.IO) {

            sequenceVisitConsulting =
                FirebaseFirestore.getInstance().collection("SequenceVisitConsulting")
                    .document("Data")
                    .get().await()
                    .getLong("value")?.toInt()!!

            sequenceVisitConsulting
        }
        suspend fun updateSequence(sequenceVisitConsulting:Int) = withContext(Dispatchers.IO) {
            val documentReference = FirebaseFirestore.getInstance().collection("SequenceVisitConsulting")
                .document("Data")

            val map = mutableMapOf<String, Long>()
            map["value"] = sequenceVisitConsulting.toLong()

            documentReference.set(map)
        }

        suspend fun insertApplication(visitConsulting: VisitConsulting)= withContext(Dispatchers.IO) {
            FirebaseFirestore.getInstance().collection("VisitConsulting")
                .add(visitConsulting)
        }

        suspend fun getVisitData(){

        }

        suspend fun getVisitList(){

        }
    }
}