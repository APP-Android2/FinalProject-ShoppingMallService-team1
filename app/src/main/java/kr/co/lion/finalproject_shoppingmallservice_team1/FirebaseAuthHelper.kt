package kr.co.lion.finalproject_shoppingmallservice_team1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.UserDao
import kr.co.lion.finalproject_shoppingmallservice_team1.model.User
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.login.LoginActivity

class FirebaseAuthHelper {

    companion object {
        private lateinit var auth: FirebaseAuth  // Firebase 인증 객체

        // Firebase 인증 초기화
        fun initializeFirebaseAuth() {
            auth = FirebaseAuth.getInstance()
        }

        // 현재 로그인된 사용자 가져오기
        fun getCurrentUser() = auth.currentUser

        // GoogleSignInClient 가져오기
        fun getGoogleSignInClient(context: Context): GoogleSignInClient {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            return GoogleSignIn.getClient(context.applicationContext, gso)
        }

        // Google 로그인 결과 처리
        fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, activity: Activity, resultLauncher: ActivityResultLauncher<Intent>) {
            try {
                val account = completedTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!, activity, resultLauncher)
            } catch (e: ApiException) {
                Log.w("test1234", "구글 회원가입 오류, 구글 로그인 실패 code = ${e.statusCode}")
            }
        }

        // Google 계정으로 Firebase 인증
        private fun firebaseAuthWithGoogle(idToken: String, activity: Activity, resultLauncher: ActivityResultLauncher<Intent>) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->

                if (task.isSuccessful) {

                    // 새로운 사용자인지 확인
                    val isNewUser = task.result.additionalUserInfo?.isNewUser

                    CoroutineScope(Dispatchers.IO).launch {
                        if (isNewUser == true){
                            val currentUser = auth.currentUser
                            currentUser?.let {
                                val user = User(
                                    userId = it.uid,
                                    name = it.displayName.orEmpty(),
                                    email = it.email.orEmpty())

                                UserDao.addUser(user)
                            }
                        }

                        withContext(Dispatchers.Main){
                            resultLauncher.launch(Intent(activity, NavigationActivity::class.java))
                            activity.finish()
                            Log.d("test1234", "로그인 성공")
                        }
                    }

                } else {
                    Log.w("test1234", "구글 회원가입 오류, 구글 로그인 실패, ${task.exception}")
                }
            }
        }

        // 로그아웃
        fun signOut(activity: Activity) {

            // Firebase 로그아웃
            auth.signOut()

            // 이전 Activity 스택을 정리 후 LoginActivity로 이동
            val intent = Intent(activity, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            activity.startActivity(intent)

            // 현재 activity 종료
            activity.finish()

            Log.d("test1234", "로그아웃 완료")
        }

        // 회원탈퇴
        fun deleteUserAccount(activity: Activity){

            val user = FirebaseAuth.getInstance().currentUser

            user?.delete()?.addOnCompleteListener { task ->

                if(task.isSuccessful){
                    Log.d("test1234", "사용자 계정이 정상적으로 삭제 완료")
                    // 회원탈퇴 후 처리할 로직

                    // 이전 Activity 스택을 정리 후 LoginActivity로 이동
                    val intent = Intent(activity, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    activity.startActivity(intent)

                } else {
                    Log.d("test1234", "사용자 계정 삭제 실패", task.exception)
                }
            }
        }
    }
}