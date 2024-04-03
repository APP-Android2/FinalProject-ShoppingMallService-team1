package kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel


import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButtonToggleGroup

class LoginSignUp1ViewModel : ViewModel() {

    val totalCheckBox = MutableLiveData<Boolean>()
    val CheckBox1 = MutableLiveData<Boolean>()
    val CheckBox2 = MutableLiveData<Boolean>()
    val CheckBox3 = MutableLiveData<Boolean>()
    val CheckBox4 = MutableLiveData<Boolean>()

    val agreeButtonEnabled = MutableLiveData<Boolean>()
    val agreeButtonAlpha = MutableLiveData<Float>()

    init {
        resetState()
    }

    fun resetState() {
        totalCheckBox.value = false
        CheckBox1.value = false
        CheckBox2.value = false
        CheckBox3.value = false
        CheckBox4.value = false
        agreeButtonEnabled.value = false
        agreeButtonAlpha.value = 0.5f
    }

    fun onClickTotalCheckBox(){

        val totalChk = (totalCheckBox.value == true)

        CheckBox1.value = totalChk
        CheckBox2.value = totalChk
        CheckBox3.value = totalChk
        CheckBox4.value = totalChk
        agreeButtonEnable()
    }

    fun onClickCheckBox(){

        totalCheckBox.value = (CheckBox1.value == true && CheckBox2.value == true && CheckBox3.value == true && CheckBox4.value == true)
        agreeButtonEnable()
    }

    fun agreeButtonEnable(){

        val chk = (CheckBox1.value == true && CheckBox2.value == true && CheckBox3.value == true)

        agreeButtonEnabled.value = chk
        agreeButtonAlpha.value = if(chk) 1.0f else 0.5f
    }

    companion object{

        // ViewModel에 값을 설정하여 화면에 반영하는 작업을 할때 호출된다.
        // () 안에는 속성의 이름을 넣어준다.
        // 속성의 이름은 자유롭게 해주면 되지만 기존의 속성 이름과 중복되지 않게 해줘야 한다.
        @BindingAdapter("android:checkedButtonId")
        @JvmStatic
        // 매개변수 : 값이 설정된 View 객체, ViewModel을 통해 설정되는 값
        fun setCheckedButtonId(group: MaterialButtonToggleGroup, buttonId: Int){
            group.check(buttonId)
        }

        // 값을 속성에 넣어주는 것을 순방향이라고 부른다.
        // 반대로 속성의 값이 변경되어 MutableLive 데이터로 전달하는 것을 역방향이라고 한다.
        // 순방향만 구현해주면 단방향이 되는 것이고 순방향과 역방향을 모두 구현해주면 양방향
        // 화면 요소가 가진 속성애 새로운 값이 설정되면 ViewModel의 변수에 값이 설정될 때 호출된다.
        // 리스너 역할을 할 속성을 만들어준다.
        @BindingAdapter("checkedButtonChangeListener")
        @JvmStatic
        fun checkedButtonChangeListener(group: MaterialButtonToggleGroup, inverseBindingListener: InverseBindingListener){
            group.addOnButtonCheckedListener { group, checkedId, isChecked ->
                inverseBindingListener.onChange()
            }
        }

        // 역방향 바인딩이 벌어질 때 호출된다.
        @InverseBindingAdapter(attribute = "android:checkedButtonId", event="checkedButtonChangeListener")
        @JvmStatic
        fun getCheckedButtonId(group: MaterialButtonToggleGroup):Int{
            return group.checkedButtonId
        }
    }
}