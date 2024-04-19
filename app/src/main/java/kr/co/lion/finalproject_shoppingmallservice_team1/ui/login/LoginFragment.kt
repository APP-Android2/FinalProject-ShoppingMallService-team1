package kr.co.lion.finalproject_shoppingmallservice_team1.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kr.co.lion.finalproject_shoppingmallservice_team1.LOGIN_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentLoginBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.FirebaseAuthHelper

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var loginActivity: LoginActivity

    private lateinit var signInResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        loginActivity = activity as LoginActivity

        FirebaseAuthHelper.initializeFirebaseAuth()  // Firebase 인증 초기화

        initializeSignInResultLauncher() // ActivityResultLauncher 초기화
        checkLoggedInUser() // 로그인된 사용자 확인

        googleLogin() // Google 로그인

        kakaoLogin()
        naverLogin()

        return fragmentLoginBinding.root
    }

    // ActivityResultLauncher 초기화
    private fun initializeSignInResultLauncher(){
        signInResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == AppCompatActivity.RESULT_OK){
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                FirebaseAuthHelper.handleSignInResult(task, loginActivity, signInResultLauncher)
            }
        }
    }

    // 로그인된 사용자 확인
    private fun checkLoggedInUser(){

        val currentUser = FirebaseAuthHelper.getCurrentUser()
        if (currentUser != null) {

            // 사용자가 이미 로그인되어 있으면 NavigationActivity로 이동
            startActivity(Intent(loginActivity, NavigationActivity::class.java))
            loginActivity.finish()
        }
    }

    // Google 로그인
    private fun googleLogin() {
        fragmentLoginBinding.googleloginButton.setOnClickListener {
            val googleSignInClient = FirebaseAuthHelper.getGoogleSignInClient(loginActivity)
            val signInIntent = googleSignInClient.signInIntent
            signInResultLauncher.launch(signInIntent)
        }
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
}