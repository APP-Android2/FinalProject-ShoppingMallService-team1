package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kr.co.lion.finalproject_shoppingmallservice_team1.LOGIN_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.LoginActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var loginActivity: LoginActivity
    private lateinit var navigationActivity: NavigationActivity

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        loginActivity = activity as LoginActivity
        // Firebase 앱 초기화
        FirebaseApp.initializeApp(loginActivity)

        auth = Firebase.auth

        if (auth.currentUser != null) {
            val intent = Intent(loginActivity, NavigationActivity::class.java)
            startActivity(intent)
            loginActivity.finish()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("524744687587-u0ln40022gc6pe0ijohht8jfau4prtj3.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(loginActivity, gso)

        fragmentLoginBinding.googleloginButton.setOnClickListener {
            signIn()
        }


        kakaoLogin()
        naverLogin()
        googleLogin()

        return fragmentLoginBinding.root
    }
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, REQ_ONE_TAP)
        Log.d("test1234", "signIn")
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) { //update ui code here
        if (user != null) {
            val intent = Intent(loginActivity, NavigationActivity::class.java)
            startActivity(intent)
            loginActivity.finish()
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(loginActivity,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = auth.currentUser
                        Log.d("test1234", "GoogleLogin")
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        updateUI(null)
                    }
                })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQ_ONE_TAP) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
                Log.d("test1234", "account")

            } catch (e: ApiException) {
                Log.d("test1234", "${e}")
            }
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

    private fun googleLogin(){

        fragmentLoginBinding.loginGoogleloginButton.setOnClickListener {

            val intent = Intent(loginActivity, NavigationActivity::class.java)
            startActivity(intent)

            loginActivity.finish()
        }
    }

}