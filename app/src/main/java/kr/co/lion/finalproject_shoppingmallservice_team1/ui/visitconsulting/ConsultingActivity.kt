package kr.co.lion.finalproject_shoppingmallservice_team1.ui.visitconsulting

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.co.lion.finalproject_shoppingmallservice_team1.CONSULTING_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityConsultingBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.model.VisitConsulting
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.visitconsulting.viewmodel.ActivityConsultingViewModel

class ConsultingActivity : AppCompatActivity() {

    lateinit var activityConsultingBinding: ActivityConsultingBinding
    lateinit var activityConsultingViewModel: ActivityConsultingViewModel

    // 프래그먼트의 주소값을 담을 프로퍼티
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityConsultingBinding = DataBindingUtil.setContentView(this, R.layout.activity_consulting)
        activityConsultingViewModel = ViewModelProvider(this).get(ActivityConsultingViewModel::class.java)
        activityConsultingBinding.activityConsultingViewModel = activityConsultingViewModel
        activityConsultingBinding.lifecycleOwner = this

        setContentView(activityConsultingBinding.root)

        // ConsultingCalendarFragment에서 전달한 데이터를 받기 위해 FragmentManager 사용
        supportFragmentManager.setFragmentResultListener("consultingDate", this) { _, bundle ->
            val currentDate = bundle.getString("currentDate")

            activityConsultingBinding.editTextDateConsulting.text = currentDate
            activityConsultingBinding.editTextTimeConsulting
        }
        settingConsultingToolbar()
        settingConsultingButtonClick()
        settingCalendarImageClick()
        settingTime()

    }

    /**
     * 함수 정리 (작성 순서)
     * 1. 툴바 설정 함수
     * 2. 버튼 설정 함수
     * 3. 달력 화면을 띄우는 함수
     * 4. 시간 설정 함수
     * 5. 바텀 시트 올리는 함수
     * 6. 날짜 설정 함수
     * 7. Fragment 교체 함수
     */

    fun settingConsultingToolbar(){
        activityConsultingBinding.apply {
            toolbarConsulting.apply {
                setNavigationIcon(R.drawable.close)
                setNavigationOnClickListener {
                    finish()
                }
            }
        }
    }

    fun settingConsultingButtonClick(){
        activityConsultingBinding.apply {
            consultingAddButton.apply {
                setOnClickListener {
                    Log.d("test1234", "finish")
                    activityConsultingViewModel?.updateData()
                    finish()
                }
            }
        }
    }

    fun settingCalendarImageClick(){
        activityConsultingBinding.apply {
            imageViewConsultingDate.apply {
                setOnClickListener {
                    onImageClick(it)
                }
            }
        }
    }

    fun onImageClick(view: View){
        when(view.id){
            R.id.imageViewConsultingDate -> {
                replaceFragment(CONSULTING_FRAGMENT_NAME.CONSULTING_CALENDAR_FRAGMENT, true, true, null)
            }
        }
    }

    fun settingTime(){
        activityConsultingBinding.editTextTimeConsulting.setOnClickListener {
            showConsultingCalendarBottomSheet()
        }
    }

    // 시간대를 보여불 BottomSheet를 띄워준다.
    fun showConsultingCalendarBottomSheet(){
        val consultingCalendarBottomFragment = ConsultingCalendarBottomFragment()
        consultingCalendarBottomFragment.show(supportFragmentManager, "ConsultingCalendarBottomSheet")
    }

    // 선택된 텍스트를 받아 editTextTimeConsulting에 설정
    fun setSelectedTime(selectedTime: String) {
        activityConsultingBinding.editTextTimeConsulting.append("${selectedTime} ")

    }
    fun replaceFragment(name: CONSULTING_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            CONSULTING_FRAGMENT_NAME.CONSULTING_CALENDAR_FRAGMENT -> {
                newFragment = ConsultingCalendarFragment()
            }
            CONSULTING_FRAGMENT_NAME.CONSULTING_A -> {

            }
        }

        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){
            if(isAnimate == true){

                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }
            fragmentTransaction.replace(R.id.consultingActivityReplace, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }

    // BackStack에서 Fragment를 제거한다.
    fun removeFragment(name: CONSULTING_FRAGMENT_NAME){
        SystemClock.sleep(200)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}