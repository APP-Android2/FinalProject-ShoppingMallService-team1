package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.finalproject_shoppingmallservice_team1.LOGIN_SIGNUP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentLoginSignUp2Binding

class LoginSignUp2Fragment : Fragment() {

    private lateinit var fragmentLoginSignUp2Binding: FragmentLoginSignUp2Binding
    private lateinit var loginSignUpFragment: LoginSignUpFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentLoginSignUp2Binding = FragmentLoginSignUp2Binding.inflate(inflater)
        loginSignUpFragment = parentFragment as LoginSignUpFragment

        loginSignUpFragment.settingToolbar(LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP2_FRAGMENT)
        setProfileImage()
        checkNickName()
        signUpFinishButton()
        handleBackPress()

        return fragmentLoginSignUp2Binding.root
    }

    private fun signUpFinishButton(){

        fragmentLoginSignUp2Binding.loginSignup2Finishbutton.setOnClickListener {

            val intent = Intent(loginSignUpFragment.loginActivity, NavigationActivity::class.java)
            startActivity(intent)

            loginSignUpFragment.loginActivity.finish()
        }
    }

    private fun setProfileImage(){

        fragmentLoginSignUp2Binding.loginsignup2Profile.setOnClickListener {

            MaterialAlertDialogBuilder(loginSignUpFragment.loginActivity).apply {
                setTitle("프로필 사진")
                setItems(arrayOf("사진 촬영", "앨범에서 사진 선택", "기본 이미지로 변경")){ dialog, which ->

                    SystemClock.sleep(200)

                    when (which) {

                        // 사진 촬영을 선택한 경우
                        0 -> {}

                        // 앨범에서 사진 선택을 선택한 경우
                        1 -> {}

                        // 기본 이미지로 변경을 선택한 경우
                        2 -> {}
                    }
                }
                show()
            }
        }
    }

    private fun checkNickName(){
        fragmentLoginSignUp2Binding.apply {
            loginSignup2Nicknamebutton.setOnClickListener {
                loginSignup2Finishbutton.apply {
                    isEnabled = true
                    alpha = 1.0f
                }
            }
        }
    }

    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                loginSignUpFragment.removeFragment(LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP2_FRAGMENT)
                loginSignUpFragment.settingProgressBar(100, 50)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}