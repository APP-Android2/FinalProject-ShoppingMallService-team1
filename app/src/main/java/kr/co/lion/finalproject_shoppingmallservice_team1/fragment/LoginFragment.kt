package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
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
import com.google.firebase.firestore.FirebaseFirestore
import kr.co.lion.finalproject_shoppingmallservice_team1.GoogleLoginUser
import kr.co.lion.finalproject_shoppingmallservice_team1.LOGIN_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.LoginActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var loginActivity: LoginActivity

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    // 로그인한 유저 수
    var userCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        loginActivity = activity as LoginActivity

        // Firebase 앱 초기화
        FirebaseApp.initializeApp(loginActivity)

        // 권한 선언
        auth = Firebase.auth

        // 로그인 한 후에 app을 켰을때 바로 다음 화면으로 넘어가게 함
        if (auth.currentUser != null) {
            val intent = Intent(loginActivity, NavigationActivity::class.java)
            startActivity(intent)
            loginActivity.finish()
        }

        // 사용자의 구글 계정으로 앱에 로그인할 때 필요한 옵션을 설정
        // ID 토큰과 Email 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("524744687587-u0ln40022gc6pe0ijohht8jfau4prtj3.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(loginActivity, gso)


        kakaoLogin()
        naverLogin()
        googleLogin()

        return fragmentLoginBinding.root
    }

    //Intent를 이용해 구글로그인 페이지로 가서  Google Sign In flow 시작
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, REQ_ONE_TAP)
        Log.d("test1234", "signIn")
    }

    // 작업을 초기화할 때 사용자가 현재 로그인되어 있는지 확인
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    // 로그인 성공 여부 확인 후 액티비티에 사용자 ID 토큰, 계정 객체 전달
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 구글 로그인 인텐트의 결과 값을 통해 로그인 성공 여부 확인
        if (requestCode == REQ_ONE_TAP) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                // GoogleSignInAccount 객체에서 ID 토큰을 가져와서 firebaseAuthWithGoogle함수로 전달
                firebaseAuthWithGoogle(account.idToken!!, account)

            } catch (e: ApiException) {
                // 계정 전달하지 않고 종료
                Log.d("test1234", "${e}")
            }
        }
    }


    // 사용자의 ID 토큰으로 Firebase 사용자 인증 정보로 변경하여 인증
    private fun firebaseAuthWithGoogle(idToken:String, account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(loginActivity,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        val isNewUser = task.result.additionalUserInfo?.isNewUser

                        // 사용자 인증 정보를 UI에 업데이트
                        val user: FirebaseUser? = auth.currentUser

                        googleDocument(
                            GoogleLoginUser(
                                uid = user?.uid,
                                displayName = user?.displayName,
                                email = user?.email
                            )
                        )

                        Log.d("test1234", "${account.email}님 GoogleLogin")
                        updateUI(user)
                    } else {
                        // 인증 실패
                        updateUI(null)
                    }
                })
    }

    // 로그인 성공 후 UI 업데이트
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(loginActivity, NavigationActivity::class.java)
            startActivity(intent)
            loginActivity.finish()
        }
    }

    private fun googleDocument(data:GoogleLoginUser){
        FirebaseFirestore.getInstance()
            .collection("GoogleLoginUser")
            .document(data.uid!!)
            .set(data)
            .addOnSuccessListener {
                Log.d("test1234", "Google DB Success")
            }
            .addOnFailureListener {
                Log.d("test1234", "Google DB Fail")
            }
        userCount++
    }

    private fun googleLogin(){
        fragmentLoginBinding.googleloginButton.setOnClickListener {
            signIn()
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