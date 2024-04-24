package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.dao

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.POST_STATUS
import kr.co.lion.finalproject_shoppingmallservice_team1.model.TrainerReview
import java.io.File

class TrainerReviewDao {

    companion object{

        //trainerPostId를 받아서 해당 게시판의 트레이너 리뷰만 보이도록 한다.
        suspend fun gettingTrainerReviewList(trainerPostId: Int):MutableList<TrainerReview>{
            val trainerReviewList = mutableListOf<TrainerReview>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("TrainerReviewMaster")
                var query = collectionReference.whereEqualTo("reviewStatus", POST_STATUS.POST_STATUS_NORMAL.number)
                query = query.whereEqualTo("trainerPostId", trainerPostId)

                query = query.orderBy("createDate", Query.Direction.DESCENDING)

                val queryShapshot = query.get().await()

                // 위에서 가져온 문서의 수 만큼 반복한다.
                queryShapshot.forEach{
                    val trainerReview = it.toObject(TrainerReview::class.java)
                    trainerReviewList.add(trainerReview)
                }
            }
            job1.join()

            return trainerReviewList
        }

        // 트레이너 리뷰 시퀀스값을 가져온다.
        suspend fun gettingTrainerReviewSequence(): Int{

            var trainerReviewSequence = 0

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")

                // 게시판 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("TrainerReviewSequence")

                // 문서내에 있는 데이터를 가져올 수 있는 객체를 가져온다.
                val documentSnapShot = documentReference.get().await()
                trainerReviewSequence = documentSnapShot.getLong("value")?.toInt()!!
            }
            job1.join()
            return trainerReviewSequence
        }

        // 트레이너 리뷰 시퀀스 값을 업데이트 한다.
        suspend fun updateTrainerReviewSequence(trainerReviewSequence: Int){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 게시판 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("TrainerReviewSequence")
                // 저장할 데이터를 담을 HashMap을 만들어준다.
                // value 라는 필드가 있으면 덮어쓰기 하고, 없으면 만들어준다.
                val map = mutableMapOf<String, Long>()
                map["value"] = trainerReviewSequence.toLong()
                // 저장한다.
                documentReference.set(map)
            }
            job1.join()
        }

        // 트레이너 리뷰 데이터를 저장한다.
        suspend fun insertTrainerReviewData(trainerReview: TrainerReview){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("TrainerReviewMaster")
                collectionReference.add(trainerReview)
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
                val storageRef = Firebase.storage.reference.child("trainerReviewImage/$uploadFileName")
                // 업로드한다. (단말기 상의 경로 파일객체)
                storageRef.putFile(uri)
            }
            job1.join()
        }

        // 이미지 데이터를 받아오는 메서드
        suspend fun gettingTrainerReviewImage(context:Context, imageFileName:String, imageView: ImageView){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val storageRef = Firebase.storage.reference.child("trainerReviewImage/$imageFileName")
                val imageUri = storageRef.downloadUrl.await()
                val job2 = CoroutineScope(Dispatchers.Main).launch {
                    Glide.with(context).load(imageUri).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable> {
                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: Target<Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                // 이미지 로딩 성공 시 처리할 작업
                                imageView.visibility = View.VISIBLE
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                // 이미지 로딩 실패 시 처리할 작업
                                // false를 반환하여 이미지 로딩을 계속 처리하도록 한다.
                                return false
                            }
                        })
                        .into(imageView)
                }
                job2.join()
            }
            job1.join()
        }

    }
}