package kr.co.lion.finalproject_shoppingmallservice_team1

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityReadTrainerBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.fragment.ReadTrainerFragment
import kr.co.lion.finalproject_shoppingmallservice_team1.fragment.ReadTrainerTab1Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.fragment.ReadTrainerTab2Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.fragment.ReadTrainerTab3Fragment

class ReadTrainerActivity : AppCompatActivity() {

    lateinit var activityReadTrainerBinding: ActivityReadTrainerBinding

    // 프래그먼트 객체를 담을 변수
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    var isImageClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityReadTrainerBinding = ActivityReadTrainerBinding.inflate(layoutInflater)
        setContentView(activityReadTrainerBinding.root)

        myPickButtonClick()

        replaceFragment(TRAINER_FRAGMENT_NAME.READ_TRAINER_FRAGMENT, false, false, null)
    }


    /**
     * 함수 정리 (작성 순서)
     * 1. 찜 버튼 이벤트 설정 (myPickButtonClick())
     * 2. 찜 버튼 이벤트 조건문 (updateImageButton())
     * 3. ReadTrainerFragment 실행을 위함
     */


    fun myPickButtonClick(){
        activityReadTrainerBinding.apply {
            readTrainerMyPickImageButton.setOnClickListener {
                isImageClick = !isImageClick
                updateImageButton()
            }
        }
    }

    //
    fun  updateImageButton(){
        activityReadTrainerBinding.apply {
            if(isImageClick){
                readTrainerMyPickImageButton.setImageResource(R.drawable.favorite_fill)
                Toast.makeText(this@ReadTrainerActivity, "'찜' 선택 되었습니다.", Toast.LENGTH_SHORT).show()
            } else{
                readTrainerMyPickImageButton.setImageResource(R.drawable.favorite)
                Toast.makeText(this@ReadTrainerActivity, "'찜' 해지 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun replaceFragment(name: TRAINER_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            TRAINER_FRAGMENT_NAME.READ_TRAINER_FRAGMENT -> {
                newFragment = ReadTrainerFragment()
            }
            TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB1_FRAGMENT -> {
                newFragment = ReadTrainerTab1Fragment()
            }
            TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB2_FRAGMENT -> {
                newFragment = ReadTrainerTab2Fragment()
            }
            TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB3_FRAGMENT -> {
                newFragment = ReadTrainerTab3Fragment()
            }
        }

        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){

            // 애니메이션 설정
            if(isAnimate == true){

                if(oldFragment != null){
                    // old에서 new가 새롭게 보여질 때 old의 애니메이션
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    // new에서 old로 되돌아갈때 old의 애니메이션
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }

                // old에서 new가 새롭게 보여질 때 new의 애니메이션
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                // new에서 old로 되돌아갈때 new의 애니메이션
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }

            fragmentTransaction.replace(R.id.readTrainerContainerView, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }
}