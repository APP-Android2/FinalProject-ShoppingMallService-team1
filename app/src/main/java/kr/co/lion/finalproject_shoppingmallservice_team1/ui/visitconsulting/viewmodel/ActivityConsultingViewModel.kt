package kr.co.lion.finalproject_shoppingmallservice_team1.ui.visitconsulting.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.model.VisitConsulting
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.visitconsulting.VisitConsultingDao

class ActivityConsultingViewModel:ViewModel() {
    val editTextNameConsulting = MutableLiveData<String>()
    val editTextPurposeConsulting = MutableLiveData<String>()
    val editTextDateConsulting = MutableLiveData<String>()
    val editTextEtcConsulting = MutableLiveData<String>()


    fun updateData() {
        CoroutineScope(Dispatchers.Main).launch {

            val sequence = VisitConsultingDao.getSequence()
            VisitConsultingDao.updateSequence(sequence + 1)

            val visitConsultingId = sequence + 1
            var centerId = ""
            var trainerId = ""
            val name = editTextNameConsulting.value!!
            val exercisePurpose = editTextPurposeConsulting.value!!
            val applicationTime = editTextDateConsulting.value!!
            val etcContent = editTextEtcConsulting.value

            val stateCheck = true

            val visitConsulting = VisitConsulting(visitConsultingId, centerId, trainerId, name, exercisePurpose,
                applicationTime, etcContent, stateCheck)
            Log.d("test1234", "${visitConsulting.visitConsultingId}")
            Log.d("test1234", "${visitConsulting.name}")

            VisitConsultingDao.insertApplication(visitConsulting)
        }
    }
}