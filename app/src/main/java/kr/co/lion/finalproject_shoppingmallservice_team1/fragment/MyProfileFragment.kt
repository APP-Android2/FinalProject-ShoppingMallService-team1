package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kr.co.lion.finalproject_shoppingmallservice_team1.LoginActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {

    lateinit var fragmentMyProfileBinding: FragmentMyProfileBinding
    lateinit var navigationActivity: NavigationActivity

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        navigationActivity = activity as NavigationActivity

        auth = Firebase.auth

        settingToolbar()
        handleBackPress()
        settingEvent()

        return fragmentMyProfileBinding.root
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
            }
        }
    }

    // 뒤로가기 처리
    private fun backProcess(){
        SystemClock.sleep(200)
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
        FirebaseAuth.getInstance().signOut()

        startActivity(Intent(navigationActivity, LoginActivity::class.java))
        navigationActivity.finish()
    }

    private fun revokeAccess(){

        auth.currentUser?.delete()?.addOnCompleteListener { task ->

            if(task.isSuccessful){
                startActivity(Intent(navigationActivity, LoginActivity::class.java))
                navigationActivity.finish()
            }
        }
    }
}