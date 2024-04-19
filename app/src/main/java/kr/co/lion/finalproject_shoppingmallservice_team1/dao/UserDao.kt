package kr.co.lion.finalproject_shoppingmallservice_team1.dao

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kr.co.lion.finalproject_shoppingmallservice_team1.model.User

class UserDao {
    companion object {

        // 유저 정보 추가
        suspend fun addUser(user: User) {
            val db = FirebaseFirestore.getInstance()
            try {
                db.collection("users").document(user.userId).set(user).await()
                Log.d("test1234", "유저 데이터 추가 성공")
            } catch (e: Exception) {
                Log.d("test1234", "유저 데이터 추가 실패, 에러: ${e.message}")
            }
        }

        // 유저 정보 가져오기
        suspend fun getUser(uid: String): User? = withContext(Dispatchers.IO) {
            val db = FirebaseFirestore.getInstance()
            try {
                val documentSnapshot = db.collection("users").document(uid).get().await()
                documentSnapshot.toObject<User>()
            } catch (e: Exception) {
                Log.d("test1234", "사용자 데이터 가져오기 실패 ${e.message}")
                null
            }
        }

        // 유저 정보 update
        suspend fun updateUser(uid: String, user: User): Boolean = withContext(Dispatchers.IO) {
            val db = FirebaseFirestore.getInstance()

            val userHashMap = hashMapOf<String, Any>()
            userHashMap["name"] = user.name
            userHashMap["gender"] = user.gender
            userHashMap["email"] = user.email
            userHashMap["phoneNumber"] = user.phoneNumber

            try {
                db.collection("users").document(uid).update(userHashMap).await()
                Log.d("test1234", "사용자 데이터 업데이트 완료")
                true // 업데이트 완료
            } catch (e: Exception) {
                Log.d("test1234", "사용자 데이터 업데이트 실패", e)
                false // 업데이트 실패
            }
        }
    }
}