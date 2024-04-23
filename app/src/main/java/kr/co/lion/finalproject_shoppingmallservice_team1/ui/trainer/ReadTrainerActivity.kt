package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.TRAINER_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityReadTrainerBinding

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

        // 항목 Item 클릭할 때, 넘어온 게시판ID 데이터
        val trainerPostIdIntent = intent.getIntExtra("trainerPostId", 0)

        val trainerBundle = Bundle()
        trainerBundle.putInt("trainerPostId", trainerPostIdIntent)

        myPickButtonClick()
        purchasingButtonClick()

        replaceFragment(TRAINER_FRAGMENT_NAME.READ_TRAINER_FRAGMENT, false, false, trainerBundle)
    }


    /**
     * 함수 정리 (작성 순서)
     * 1. 찜 버튼 이벤트 설정 (myPickButtonClick())
     * 2. 찜 버튼 이벤트 조건문 (updateImageButton())
     * 3. 구매하기 버튼 이벤트 설정
     * 4. 구매하기의 옵션을 보여 주기 위한 BottomSheet를 띄워준다.
     * 5. ReadTrainerFragment 실행을 위함
     */

    fun myPickButtonClick(){
        activityReadTrainerBinding.apply {
            readTrainerMyPickImageButton.setOnClickListener {
                isImageClick = !isImageClick
                updateImageButton()
            }
        }
    }

    fun updateImageButton(){
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

    fun purchasingButtonClick(){
        activityReadTrainerBinding.apply {
            trainerPurchasingButton.apply {
                setOnClickListener {
                    showReadTrainerPurchasingBottomSheet()
                }
            }
        }
    }
    
    fun showReadTrainerPurchasingBottomSheet(){
        val readTrainerPurchasingBottomSheet = ReadTrainerPurchasingBottomFragment()
        readTrainerPurchasingBottomSheet.show(supportFragmentManager, "ReadTrainerPurchasingBottomSheet")
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