package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.dao

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.POST_STATUS
import kr.co.lion.finalproject_shoppingmallservice_team1.TRAINER_POST_TYPE
import kr.co.lion.finalproject_shoppingmallservice_team1.model.TrainerPost
import java.io.File

class TrainerDao {

    companion object{

        // 트레이너 게시판 시퀀스값을 가져온다.
        suspend fun gettingTrainerPostSequence(): Int{

            var trainerPostSequence = 0

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")

                // 게시판 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("TrainerPostSequence")

                // 문서내에 있는 데이터를 가져올 수 있는 객체를 가져온다.
                val documentSnapShot = documentReference.get().await()
                trainerPostSequence = documentSnapShot.getLong("value")?.toInt()!!
            }
            job1.join()
            return trainerPostSequence
        }

        // 트레이너 게시판 시퀀스 값을 업데이트 한다.
        suspend fun updateTrainerPostSequence(trainerPostSequence: Int){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 게시판 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("TrainerPostSequence")
                // 저장할 데이터를 담을 HashMap을 만들어준다.
                // value 라는 필드가 있으면 덮어쓰기 하고, 없으면 만들어준다.
                val map = mutableMapOf<String, Long>()
                map["value"] = trainerPostSequence.toLong()
                // 저장한다.
                documentReference.set(map)
            }
            job1.join()
        }

        // 트레이너 게시판 정보를 저장한다
        suspend fun insertTrainerPostData(trainerPost: TrainerPost){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("TrainerPostMaster")
                collectionReference.add(trainerPost)
            }
            job1.join()
        }

        // 이미지 데이터를 firebase storage에 업로드는 메서드
        suspend fun uploadImage(context: Context, fileName:String, uploadFileName:String){
            // 외부저장소 까지의 경로를 가져온다.
            val filePath = context.getExternalFilesDir(null).toString()
            // 서버로 업로드할 파일의 경로
            val file = File("${filePath}/${fileName}")
            // 단말기 상 파일경로
            val uri = Uri.fromFile(file)

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // Storage에 접근할 수 있는 객체를 가져온다
                val storageRef = Firebase.storage.reference.child("trainerPostImage/$uploadFileName")
                // 업로드한다. (단말기 상의 경로 파일객체)
                storageRef.putFile(uri)
            }
            job1.join()
        }

        // 이미지 데이터를 받아오는 메서드
        suspend fun gettingTrainerPostProfileImage(context:Context, imageFileName:String, imageView: ImageView){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val storageRef = Firebase.storage.reference.child("trainerPostImage/$imageFileName")
                val imageUri = storageRef.downloadUrl.await()
                val job2 = CoroutineScope(Dispatchers.Main).launch {
                    Glide.with(context).load(imageUri).into(imageView)
                    imageView.visibility = View.VISIBLE
                }
                job2.join()
            }
            job1.join()
        }

        // 게시글 목록을 가져온다.
        suspend fun gettingTrainerPostList(trainerType: String):MutableList<TrainerPost>{
            val trainerPostList = mutableListOf<TrainerPost>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("TrainerPostMaster")
                var query = collectionReference.whereEqualTo("postStatus", POST_STATUS.POST_STATUS_NORMAL.number)
                query = query.orderBy("trainerPostId", Query.Direction.DESCENDING)

                if(trainerType == TRAINER_POST_TYPE.TRAINER_TYPE_FITNESS.str){
                    query = query.whereEqualTo("trainerType", trainerType)
                }
                if(trainerType == TRAINER_POST_TYPE.TRAINER_TYPE_PILATES.str){
                    query = query.whereEqualTo("trainerType", trainerType)
                }

                val queryShapshot = query.get().await()

                // 위에서 가져온 문서의 수 만큼 반복한다.
                queryShapshot.forEach{
                    val trainerPost = it.toObject(TrainerPost::class.java)
                    trainerPostList.add(trainerPost)
                }
            }
            job1.join()

            return trainerPostList
        }

    }
}