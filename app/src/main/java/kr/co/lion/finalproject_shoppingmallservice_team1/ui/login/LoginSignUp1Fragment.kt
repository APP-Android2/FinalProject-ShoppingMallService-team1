package kr.co.lion.finalproject_shoppingmallservice_team1.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.LOGIN_SIGNUP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentLoginSignUp1Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.login.viewmodel.LoginSignUp1ViewModel

class LoginSignUp1Fragment : Fragment() {

    private lateinit var fragmentLoginSignUp1Binding: FragmentLoginSignUp1Binding
    private lateinit var loginSignUpFragment: LoginSignUpFragment
    private lateinit var loginSignUp1ViewModel: LoginSignUp1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentLoginSignUp1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_sign_up1, container, false)
        loginSignUp1ViewModel = LoginSignUp1ViewModel()
        fragmentLoginSignUp1Binding.loginSignUp1ViewModel = loginSignUp1ViewModel
        fragmentLoginSignUp1Binding.lifecycleOwner = this@LoginSignUp1Fragment

        loginSignUpFragment = parentFragment as LoginSignUpFragment

        loginSignUpFragment.settingToolbar(LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP1_FRAGMENT)
        agreeButton()

        return fragmentLoginSignUp1Binding.root
    }

    private fun agreeButton() {

        fragmentLoginSignUp1Binding.apply {

            fragmentLoginSignUp1Binding.loginSignup1Agreebutton.setOnClickListener {
                loginSignUpFragment.replaceFragment(LOGIN_SIGNUP_FRAGMENT_NAME.LOGIN_SIGNUP2_FRAGMENT, true, true, null)
                loginSignUpFragment.settingProgressBar(50, 100)
            }
        }
    }
}