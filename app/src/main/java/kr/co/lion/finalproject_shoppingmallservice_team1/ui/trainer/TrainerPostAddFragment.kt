package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.Tools
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTrainerPostAddBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.model.TrainerPost
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.dao.TrainerDao
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.viewmodel.TrainerPostAddViewModel
import java.io.File

class TrainerPostAddFragment : Fragment() {

    lateinit var fragmentTrainerPostAddBinding: FragmentTrainerPostAddBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var trainerPostAddViewModel: TrainerPostAddViewModel

    // Activity 실행을 위한 런처
    lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 촬영된 사진이 저장된 경로 정보를 가지고 있는 Uri 객체
    lateinit var contentUri: Uri

    // 이미지 첨부 여부
    var isAddPicture = false

    // 트레이너 게시판 모델 객체
    val tPost = TrainerPost()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentTrainerPostAddBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trainer_post_add, container, false)
        trainerPostAddViewModel = TrainerPostAddViewModel()
        fragmentTrainerPostAddBinding.trainerPostAddViewModel = trainerPostAddViewModel
        fragmentTrainerPostAddBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity

        settingButtonClick()
        settingCameraLauncher()
        settingAlbumLauncher()


        return fragmentTrainerPostAddBinding.root
    }

    fun settingButtonClick(){
        fragmentTrainerPostAddBinding.apply {
            // 완료 버튼
            buttonDone1.setOnClickListener {
                saveTrainerPostAdd()
                navigationActivity.replaceFragment(NAVIGATION_FRAGMENT_NAME.TRAINER_FRAGMENT, false, false, null)
            }
            // 카메라 실행 버튼
            buttonCamera.setOnClickListener {
                startCameraLauncher()
            }
            // 갤러리 실행 버튼
            buttonAlbum.setOnClickListener {
                startAlbumLauncher()
            }
            // 초기화 실행 버튼
            buttonClear.setOnClickListener {
                settingInputForm()
            }
        }
    }

    // 입력 요소 초기화 설정
    fun settingInputForm(){
        trainerPostAddViewModel.textEditTrainerName.value = ""
        trainerPostAddViewModel.textEditCenterName.value = ""
        trainerPostAddViewModel.textEditCenterLocation.value = ""
        trainerPostAddViewModel.textEditTrainerType.value = ""

        fragmentTrainerPostAddBinding.trainerProfileImageView.setImageResource(R.drawable.person_add)
        isAddPicture = false

        Tools.showSoftInput(navigationActivity, fragmentTrainerPostAddBinding.textEditTrainerName)
    }


    // 트레이너 게시판 생성하기 (저장)
    fun saveTrainerPostAdd(){
        CoroutineScope(Dispatchers.Main).launch {

            // 서버에서의 첨부 이미지 파일 이름
            var serverFileName:String? = null

            // 첨부된 이미지가 있다면..
            if(isAddPicture == true){
                // 이미지의 뷰의 이미지 데이터를 파일로 저장한다. (로컬)
                Tools.saveImageViewData(navigationActivity, fragmentTrainerPostAddBinding.trainerProfileImageView, "uploadTemp.jpg")
                // 서버에서의 파일이름 (현재시간을 사용)
                serverFileName = "image_${System.currentTimeMillis()}.jpg"
                // 서버로 업로드 한다.
                TrainerDao.uploadImage(navigationActivity, "uploadTemp.jpg", serverFileName)
            }

            // 트레이너 게시판 시퀀스 값을 가져온다.
            val trainerPostSequence = TrainerDao.gettingTrainerPostSequence()
            // 시퀀스값을 1 증가시켜 덮어쓰기.
            TrainerDao.updateTrainerPostSequence(trainerPostSequence + 1)

            // 저장할 데이터를 가져온다.
            val trainerPostId = trainerPostSequence + 1
            val centerId = "Gym"
            val centerName = trainerPostAddViewModel.textEditCenterName.value!!
            val centerLocation = trainerPostAddViewModel.textEditCenterLocation.value!!
            val centerImageUrls = tPost.centerImageUrls
            val trainerId = 1
            val trainerName = trainerPostAddViewModel.textEditTrainerName.value!!
            val aboutMePhotosUrls = tPost.aboutMePhotosUrls
            val trainerPostTopImage = tPost.trainerPostTopImage
            val trainerType = trainerPostAddViewModel.textEditTrainerType.value!!
            val postStatus = 0
            val createDate = "20240419"
            val modifyDate = ""
            val notificationText = "공지사항 내용입니다."
            val aboutMeText = "트레이너 소개입니다"
            val memberShipText = "회원권 소개입니다"
            val memberShipIdList = tPost.memberShipIdList
            val careerText = "경력사항 내용 입니다"
            val orgData = "소속 운동센터는 URL클릭(바로가기)"
            val photosUrls = tPost.photosUrls
            val reviewIdList = tPost.reviewIdList
            val reviewCount = 15
            val reviewAvg = 4.3
            val likeCheck = false
            val oneDayPt = 0

            // 저장할 데이터를 객체에 담는다.
            val trainerPost = TrainerPost(centerId, centerName, centerLocation, centerImageUrls,
                trainerId, trainerName, serverFileName!!, aboutMePhotosUrls,
                trainerPostId, trainerPostTopImage, trainerType, postStatus, createDate, modifyDate,
                notificationText, aboutMeText, memberShipText, memberShipIdList,
                careerText, orgData, photosUrls, reviewIdList, reviewCount, reviewAvg, likeCheck, oneDayPt)

            // 트레이너 게시판 데이터를 저장한다.
            Log.d("test1234", "${trainerPost}")
            TrainerDao.insertTrainerPostData(trainerPost)
        }
    }


    // 카메라 런처를 실행하는 메서드(카메라 액티비티 실행)
    fun startCameraLauncher(){
        // 촬영한 사진이 저장될 경로를 가져온다.
        contentUri = Tools.getPictureUri(navigationActivity, "kr.co.lion.finalproject_shoppingmallservice_team1.file_provider")

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
                val degree = Tools.getDegree(navigationActivity, contentUri)
                // 회전된 이미지를 구한다.
                val bitmap2 = Tools.rotateBitmap(bitmap, degree.toFloat())
                // 크기를 조정한 이미지를 구한다.
                val bitmap3 = Tools.resizeBitmap(bitmap2, 1024)

                fragmentTrainerPostAddBinding.trainerProfileImageView.setImageBitmap(bitmap3)
                isAddPicture = true

                // 사진 파일을 삭제한다.
                val file = File(contentUri.path)
                file.delete()
            }
        }
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
                        val source = ImageDecoder.createSource(navigationActivity.contentResolver, uri)
                        // Bitmap을 생성한다.
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        // 컨텐츠 프로바이더를 통해 이미지 데이터에 접근한다.
                        val cursor = navigationActivity.contentResolver.query(uri, null, null, null, null)
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
                    val degree = Tools.getDegree(navigationActivity, uri)
                    // 회전 이미지를 가져온다
                    val bitmap2 = Tools.rotateBitmap(bitmap!!, degree.toFloat())
                    // 크기를 줄인 이미지를 가져온다.
                    val bitmap3 = Tools.resizeBitmap(bitmap2, 1024)

                    fragmentTrainerPostAddBinding.trainerProfileImageView.setImageBitmap(bitmap3)
                    isAddPicture = true
                }
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

}