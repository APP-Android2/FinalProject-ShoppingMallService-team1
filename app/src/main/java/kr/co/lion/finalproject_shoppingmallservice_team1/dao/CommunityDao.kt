package kr.co.lion.finalproject_shoppingmallservice_team1.dao

import CommunityPost
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.finalproject_shoppingmallservice_team1.model.TrainerPost
import kr.co.lion.finalproject_shoppingmallservice_team1.model.VisitConsulting
import java.io.File

class CommunityDao {
    companion object{
        // 순서 값 가져오기
        suspend fun getSequence(): Int {
            var sequenceCommunity = -1

            CoroutineScope(Dispatchers.IO).launch{
                sequenceCommunity =
                    FirebaseFirestore.getInstance().collection("Sequence")
                        .document("SequenceCommunity")
                        .get().await()
                        .getLong("value")?.toInt()!!

            }.join()
            return sequenceCommunity

        }

        // 순서 값 업데이트
        suspend fun updateSequence(sequenceCommunity:Int) {
            CoroutineScope(Dispatchers.IO).launch {
                val documentReference =
                    FirebaseFirestore.getInstance().collection("Sequence")
                        .document("SequenceCommunity")
                val map = mutableMapOf<String, Long>()
                map["value"] = sequenceCommunity.toLong()

                documentReference.set(map)
            }.join()
        }

        // Firebase Database에 모델 값 추가
        suspend fun insertPost(communityPost: CommunityPost) {
            CoroutineScope(Dispatchers.IO).launch {
                FirebaseFirestore.getInstance().collection("CommunityPost")
                    .add(communityPost)
            }.join()
        }
        suspend fun getCommunityList():MutableList<CommunityPost>{
            val dataList = mutableListOf<CommunityPost>()

            CoroutineScope(Dispatchers.IO).launch {
                val query = FirebaseFirestore.getInstance().collection("CommunityPost")
                    .orderBy("communityPostId", Query.Direction.DESCENDING)

                val querySnapshot = query.get().await()

                querySnapshot.forEach {
                    val communityPost= it.toObject(CommunityPost::class.java)
                    dataList.add(communityPost)
                }
            }.join()

            return dataList
        }
        suspend fun getCommnunityPost(communityPostId:Int):CommunityPost{
            val querySnapshot = FirebaseFirestore.getInstance().collection("CommunityPost")
                .whereEqualTo("communityPostId", communityPostId)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                val communityPost = documentSnapshot.toObject(CommunityPost::class.java)
                return communityPost ?: throw IllegalStateException("Failed to parse CommunityPost")
            } else {
                throw NoSuchElementException("CommunityPost with ID $communityPostId not found")
            }
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
                val storageRef = Firebase.storage.reference.child("CommunityContentImage/$uploadFileName")
                // 업로드한다. (단말기 상의 경로 파일객체)`
                storageRef.putFile(uri)
            }
            job1.join()
        }

        // 이미지 데이터를 받아오는 메서드
        suspend fun gettingCommunityContentImage(context:Context, imageFileName:String, imageView: ImageView){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val storageRef = Firebase.storage.reference.child("CommunityContentImage/$imageFileName")
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