package kr.co.lion.finalproject_shoppingmallservice_team1.ui.center.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.Chip

class CenterViewModel:ViewModel() {

    val chipChecked = MutableLiveData<Boolean>()
    val chipChecked2 = MutableLiveData<Boolean>()
    init {
        chipChecked.value = false
        chipChecked2.value = false
    }

    fun onChipClick(view:View){
        val chip = view as Chip
        chipChecked.value = chip.isChecked
    }

    fun onChipClick2(view:View){
        val chip2 = view as Chip
        chipChecked2.value = chip2.isChecked
    }
}