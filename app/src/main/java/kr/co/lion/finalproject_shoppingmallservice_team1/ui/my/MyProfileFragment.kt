package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.co.lion.finalproject_shoppingmallservice_team1.FirebaseAuthHelper
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.Tools
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyProfileBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.my.viewmodel.MyProfileViewModel

class MyProfileFragment : Fragment() {

    lateinit var fragmentMyProfileBinding: FragmentMyProfileBinding
    lateinit var navigationActivity: NavigationActivity

    private val myProfileViewModel: MyProfileViewModel by viewModels()

    val userUid = FirebaseAuthHelper.getCurrentUser()?.uid

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        fragmentMyProfileBinding.myProfileViewModel = myProfileViewModel
        fragmentMyProfileBinding.lifecycleOwner = this@MyProfileFragment

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        handleBackPress()
        //settingInputMyProfile()
        settingEvent()

        myProfileViewModel.getUserData(userUid!!)

        return fragmentMyProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MyProfileFragment 가 실행될 때 하단바가 보이지 않도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()

        // MyProfileFragment 가 제거될 때 하단바가 보이도록
        navigationActivity.activityNavigationBinding.bottomNavigationView.isVisible = true
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyProfileBinding.apply {
            toolbarMyProfile.apply {
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back)
                setNavigationOnClickListener {
                    backProcess()
                }
                // 메뉴
                inflateMenu(R.menu.menu_my_profile)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuMyProfileDone -> {

                            CoroutineScope(Dispatchers.IO).launch {
                                myProfileViewModel?.updateUserData(userUid!!)

                                withContext(Dispatchers.Main){
                                    // 저장처리
                                    backProcess()
                                }
                            }
                        }
                    }

                    true
                }
            }
        }
    }

    // 입력 요소 설정
    fun settingInputMyProfile(){

        // 프로필 사진 값 설정
        myProfileViewModel.myProfileName.value = ""
        myProfileViewModel.myProfileNickName.value = ""
        myProfileViewModel.myProfilePhoneNumber.value = ""
        myProfileViewModel.myProfileLocation.value = ""

        if (myProfileViewModel.myProfileName.value.toString().trim().isEmpty()){
            // 키보드를 올려준다.
            Tools.showSoftInput(navigationActivity, fragmentMyProfileBinding.myProfileName)
        }
    }

    // 뒤로가기 처리
    private fun backProcess(){
        SystemClock.sleep(200)
        // 키보드를 내려준다
        Tools.hideSoftInput(navigationActivity)
        parentFragmentManager.popBackStack()
    }

    // 뒤로가기 처리(단말기)
    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로가기
                backProcess()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun settingEvent(){
        fragmentMyProfileBinding.apply {
            buttonmyProfileLogout.setOnClickListener {
                logout()
                Snackbar.make(it, "로그아웃 완료했습니다.", Snackbar.LENGTH_SHORT).show()
            }
            buttonmyProfileDeleteAccount.setOnClickListener {
                revokeAccess()
                Snackbar.make(it, "회원 탈퇴했습니다.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    private fun logout(){
        FirebaseAuthHelper.signOut(navigationActivity)
    }

    private fun revokeAccess(){
        FirebaseAuthHelper.deleteUserAccount(navigationActivity)
    }
}