package kr.co.lion.finalproject_shoppingmallservice_team1.ui.community

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.Tools
import kr.co.lion.finalproject_shoppingmallservice_team1.dao.CommunityDao
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.ActivityCommunityWriteBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCommunityPhotoBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.community.viewmodel.CommunityViewModel
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.dao.TrainerDao
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
class CommunityWriteActivity : AppCompatActivity() {
    lateinit var activityCommunityWriteBinding: ActivityCommunityWriteBinding
    lateinit var communityViewModel: CommunityViewModel

    // Activity 실행을 위한 런처
    lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 촬영된 사진이 저장된 경로 정보를 가지고 있는 Uri 객체
    lateinit var contentUri: Uri

    var imageList = mutableListOf<String>()

    // 이미지 첨부 여부
    var isAddPicture = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCommunityWriteBinding = ActivityCommunityWriteBinding.inflate(layoutInflater)
        communityViewModel = ViewModelProvider(this).get(CommunityViewModel::class.java)
        activityCommunityWriteBinding.communityViewModel = communityViewModel
        activityCommunityWriteBinding.lifecycleOwner = this

        setContentView(activityCommunityWriteBinding.root)

        settingToolbar()
        settingCameraLauncher()
        settingRecyclerView()
        settingAlbumLauncher()
        settingButton()
    }

    fun settingToolbar() {
        activityCommunityWriteBinding .apply {
            toolbarCommunityWrite.apply {
                setNavigationIcon(R.drawable.close)

                setNavigationOnClickListener {
                    finish()
                }

                inflateMenu(R.menu.menu_done)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemDone -> {
                            Log.d("test1234", "CommunityWrite")
                            CoroutineScope(Dispatchers.Main).launch {
                                val resultIntent = Intent()
                                resultIntent.putExtra("CommunityWrite", 1)
                                setResult(RESULT_OK, resultIntent)
                                // 서버에서의 첨부 이미지 파일 이름
                                var serverFileName:String? = null
                                // 서버에서의 파일이름 (현재시간을 사용)
                                serverFileName = "image_${System.currentTimeMillis()}.jpg"
                                // 서버로 업로드 한다.
                                CommunityDao.uploadImage(this@CommunityWriteActivity, "uploadTemp.jpg", serverFileName)

                                communityViewModel?.updateData(imageList)
                                finish()
                            }
                        }
                    }
                    true
                }
            }
        }
    }

    fun settingButton(){
        activityCommunityWriteBinding.apply {
            btnCamera.apply {
                setOnClickListener {
                    startCameraLauncher()
                }
            }
            btnAlbum.apply {
                setOnClickListener {
                    startAlbumLauncher()
                }
            }
            btnLocation.apply {

            }
        }
    }

    fun settingRecyclerView(){
        activityCommunityWriteBinding.apply {
            recyclerViewPhoto.apply {
                adapter = PhotoRecyclerviewAdapter()
                layoutManager = LinearLayoutManager(this@CommunityWriteActivity,
                    LinearLayoutManager.HORIZONTAL, false)

            }
        }
    }
    inner class PhotoRecyclerviewAdapter:RecyclerView.Adapter<PhotoRecyclerviewAdapter.PhotoViewHolder>(){
        inner class PhotoViewHolder(rowCommunityPhotoBinding: RowCommunityPhotoBinding):RecyclerView.ViewHolder(rowCommunityPhotoBinding.root){
            val rowCommunityPhotoBinding:RowCommunityPhotoBinding

            init {
                this.rowCommunityPhotoBinding = rowCommunityPhotoBinding

                this.rowCommunityPhotoBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
            val rowCommunityPhotoBinding = RowCommunityPhotoBinding.inflate(layoutInflater)
            val photoViewHolder = PhotoViewHolder(rowCommunityPhotoBinding)

            return photoViewHolder
        }

        override fun getItemCount(): Int {
            return imageList.size
        }

        override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
            if (isAddPicture == true){
                // 이미지를 String에서 Bitmap으로 변환하여 ImageView에 설정
                val image = Tools.stringToBitmap(imageList[position])
                holder.rowCommunityPhotoBinding.imageViewCommunityContent.setImageBitmap(image)
                holder.rowCommunityPhotoBinding.imageViewPhotoDelete.setOnClickListener {
                    imageList.removeAt(position)
                    activityCommunityWriteBinding.recyclerViewPhoto.adapter?.notifyDataSetChanged()
                }


                // 이미지의 뷰의 이미지 데이터를 파일로 저장한다. (로컬)
                Tools.saveImageViewData(this@CommunityWriteActivity, holder.rowCommunityPhotoBinding.imageViewCommunityContent
                    , "uploadTemp.jpg")

            }
        }
    }

    // 카메라 런처를 실행하는 메서드(카메라 액티비티 실행)
    fun startCameraLauncher(){
        // 촬영한 사진이 저장될 경로를 가져온다.
        contentUri = Tools.getPictureUri(this@CommunityWriteActivity, "kr.co.lion.finalproject_shoppingmallservice_team1.file_provider")

        if(contentUri != null){
            // 실행할 액티비티를 카메라 액티비티로 지정한다.
            // 단말기에 설치되어 있는 모든 애플리케이션이 가진 액티비티 중에 사진촬영이
            // 가능한 액티비가 실행된다.
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // 이미지가 저장될 경로를 가지고 있는 Uri 객체를 인텐트에 담아준다.
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
            // 카메라 액티비티 실행
            cameraLauncher.launch(cameraIntent)
        }
    }
    // 카메라 런처 설정
    fun settingCameraLauncher(){
        val contract1 = ActivityResultContracts.StartActivityForResult()
        cameraLauncher = registerForActivityResult(contract1){
            // 사진을 사용하겠다고 한 다음에 돌아왔을 경우
            if(it.resultCode == AppCompatActivity.RESULT_OK){
                // 사진 객체를 생성한다.
                val bitmap = BitmapFactory.decodeFile(contentUri.path)

                // 회전 각도값을 구한다.
                val degree = Tools.getDegree(this@CommunityWriteActivity, contentUri)
                // 회전된 이미지를 구한다.
                val bitmap2 = Tools.rotateBitmap(bitmap, degree.toFloat())
                // 크기를 조정한 이미지를 구한다.
                val bitmap3 = Tools.resizeBitmap(bitmap2, 1024)

                imageList.add(Tools.bitmapToString(bitmap3))
                isAddPicture = true

                // RecyclerView를 업데이트
                activityCommunityWriteBinding.recyclerViewPhoto.adapter?.notifyDataSetChanged()

                // 사진 파일을 삭제한다.
                val file = File(contentUri.path)
                file.delete()
            }
        }
    }


    // 앨범 런처를 실행하는 메서드
    fun startAlbumLauncher(){
        // 앨범에서 사진을 선택할 수 있도록 셋팅된 인텐트를 생성한다.
        val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // 실행할 액티비티의 타입을 설정(이미지를 선택할 수 있는 것이 뜨게 한다)
        albumIntent.setType("image/*")
        // 선택할 수 있는 파들의 MimeType을 설정한다.
        // 여기서 선택한 종류의 파일만 선택이 가능하다. 모든 이미지로 설정한다.
        val mimeType = arrayOf("image/*")
        albumIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
        // 액티비티를 실행한다.
        albumLauncher.launch(albumIntent)
    }

    // 앨범 런처 설정
    fun settingAlbumLauncher() {
        // 앨범 실행을 위한 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(contract2){
            // 사진 선택을 완료한 후 돌아왔다면
            if(it.resultCode == AppCompatActivity.RESULT_OK){
                // 선택한 이미지의 경로 데이터를 관리하는 Uri 객체를 추출한다.
                val uri = it.data?.data
                if(uri != null){
                    // 안드로이드 Q(10) 이상이라면
                    val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        // 이미지를 생성할 수 있는 객체를 생성한다.
                        val source = ImageDecoder.createSource(this@CommunityWriteActivity.contentResolver, uri)
                        // Bitmap을 생성한다.
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        // 컨텐츠 프로바이더를 통해 이미지 데이터에 접근한다.
                        val cursor = this.contentResolver.query(uri, null, null, null, null)
                        if(cursor != null){
                            cursor.moveToNext()

                            // 이미지의 경로를 가져온다.
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            // 이미지를 생성한다
                            BitmapFactory.decodeFile(source)
                        }  else {
                            null
                        }
                    }

                    // 회전 각도값을 가져온다.
                    val degree = Tools.getDegree(this@CommunityWriteActivity, uri)
                    // 회전 이미지를 가져온다
                    val bitmap2 = Tools.rotateBitmap(bitmap!!, degree.toFloat())
                    // 크기를 줄인 이미지를 가져온다.
                    val bitmap3 = Tools.resizeBitmap(bitmap2, 1024)

                    imageList.add(Tools.bitmapToString(bitmap3))
                    isAddPicture = true

                    activityCommunityWriteBinding.recyclerViewPhoto.adapter?.notifyDataSetChanged()
                }
            }
        }
    }
}