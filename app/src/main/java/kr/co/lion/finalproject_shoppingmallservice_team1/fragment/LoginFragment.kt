package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.finalproject_shoppingmallservice_team1.LOGIN_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.LoginActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var loginActivity: LoginActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        loginActivity = activity as LoginActivity

        kakaoLogin()
        naverLogin()
        googleLogin()

        return fragmentLoginBinding.root
    }

    private fun kakaoLogin(){

        fragmentLoginBinding.loginKakaologinButton.setOnClickListener {

            loginActivity.replaceFragment(LOGIN_FRAGMENT_NAME.LOGIN_SIGNUP_FRAGMENT, true, true, null)

//            val intent = Intent(loginActivity, NavigationActivity::class.java)
//            startActivity(intent)
//
//            loginActivity.finish()
        }
    }

    private fun naverLogin(){

        fragmentLoginBinding.loginNaverloginButton.setOnClickListener {

            val intent = Intent(loginActivity, NavigationActivity::class.java)
            startActivity(intent)

            loginActivity.finish()
        }
    }

    private fun googleLogin(){

        fragmentLoginBinding.loginGoogleloginButton.setOnClickListener {

            val intent = Intent(loginActivity, NavigationActivity::class.java)
            startActivity(intent)

            loginActivity.finish()
        }
    }

}