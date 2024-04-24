package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.TRAINER_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.shoppingcart.ShoppingCartActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.dao.TrainerDao
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.viewmodel.ReadTrainerViewModel


class ReadTrainerFragment : Fragment() {

    lateinit var fragmentReadTrainerBinding: FragmentReadTrainerBinding
    lateinit var readTrainerActivity: ReadTrainerActivity
    lateinit var readTrainerViewModel: ReadTrainerViewModel

    // 프래그먼트 객체를 담을 변수
    var oldFragment:Fragment? = null
    var newFragment:Fragment? = null

    // 전달 받을 게시글 Id
    var trainerPostId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadTrainerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_trainer, container, false)
        readTrainerViewModel = ViewModelProvider(this).get(ReadTrainerViewModel::class.java)
        fragmentReadTrainerBinding.readTrainerViewModel = readTrainerViewModel
        fragmentReadTrainerBinding.lifecycleOwner = this

        readTrainerActivity = activity as ReadTrainerActivity

        // 전달 받은 arguments가 null 일경우, 0번 ID 반환 (0번은 오류 게시판)
        trainerPostId = arguments?.getInt("trainerPostId")?:0

        // TabFragment에 전달 할 데이터
        val trainerTabData = Bundle()
        trainerTabData.putInt("trainerPostId", trainerPostId)

        settingToolbarReadTrainer()
        onOffsetChanged()
        settingTabLayout()
        settingTrainerPostData()

        replaceFragment(TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB1_FRAGMENT, false, false, trainerTabData)
        return fragmentReadTrainerBinding.root
    }

    /**
     * 함수 정리 (작성 순서)
     * 1. 툴바 설정 (settingToolbarReadTrainer())
     * 2. AppBarLayout 상태 설정 (onOffsetChanged())
     * 3. Tab 레이아웃 설정 (settingTabLayout())
     * 4. 서버의 데이터 보여주기 (settingTrainerPostData())
     * 5. Fragment 교체 설정 (정보, 리뷰, 상담 탭 위치) (replaceFragment())
     */

    fun settingToolbarReadTrainer(){
        fragmentReadTrainerBinding.apply {
            toolbarReadTrainer.apply {
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    readTrainerActivity.finish()
                }

                inflateMenu(R.menu.menu_trainer)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemTrainerShopping -> {
                            val shoppingCartIntent = Intent(readTrainerActivity, ShoppingCartActivity::class.java)
                            startActivity(shoppingCartIntent)
                        }
                    }
                    true
                }
            }
        }
    }

    fun onOffsetChanged(){
        fragmentReadTrainerBinding.apply {
            appbarReadTrainer.apply {
                val onOffsetChangedListener = OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    // 스크롤 위치에 따른 작업 수행
                    if (Math.abs(verticalOffset) >= appBarLayout!!.totalScrollRange) {
                        // AppBarLayout이 완전히 축소된 상태
                        toolbarReadTrainerTitle.visibility = View.VISIBLE
                        toolbarReadTrainer.setBackgroundColor(Color.WHITE)

                    } else {
                        // AppBarLayout이 축소 되지 않은 상태
                        toolbarReadTrainerTitle.visibility = View.GONE
                        toolbarReadTrainer.setBackgroundColor(Color.TRANSPARENT)
                    }
                }
                appbarReadTrainer.addOnOffsetChangedListener(onOffsetChangedListener)
            }
        }
    }


    fun settingTabLayout(){
        fragmentReadTrainerBinding.apply {
            trainerInfoTab.apply {
                val trainerTabData = Bundle()
                trainerTabData.putInt("trainerPostId", trainerPostId)

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 탭이 선택되었을 때 호출되는 메서드
                        val position = tab?.position

                        when(position){
                            0 -> {
                                replaceFragment(TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB1_FRAGMENT, false, false, trainerTabData)
                            }
                            1 -> {
                                replaceFragment(TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB2_FRAGMENT, false, false, trainerTabData)
                            }
                            2 -> {
                                replaceFragment(TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB3_FRAGMENT, false, false, trainerTabData)
                            }
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        // 선택이 해제된 탭의 경우 처리할 내용

                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        // 이미 선택된 탭이 다시 선택된 경우 처리할 내용
                    }
                })
            }
        }
    }

    fun settingTrainerPostData(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentReadTrainerBinding.apply {
                val trainerPost = TrainerDao.selectTrainerPostData(trainerPostId)

                readTrainerViewModel?.apply {
                    readTrainerNameTextView.value = trainerPost?.trainerName
                    readTrainerOrgNameTextView.value = trainerPost?.centerName
                    readTrainerLocationTextView.value = trainerPost?.centerLocation
                }
            }
        }
    }

    fun replaceFragment(name: TRAINER_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(100)

        val fragmentTransaction = childFragmentManager.beginTransaction()
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

            fragmentTransaction.replace(R.id.readTrainerTabView, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }
}