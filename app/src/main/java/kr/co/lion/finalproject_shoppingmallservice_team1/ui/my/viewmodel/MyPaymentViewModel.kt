package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.Chip

class MyPaymentViewModel : ViewModel() {
    val chipChecked = MutableLiveData<Boolean>()

    init {
        chipChecked.value = false
    }

    fun onChipClicked(view: View) {
        val chip = view as Chip
        chipChecked.value = chip.isChecked
    }
}