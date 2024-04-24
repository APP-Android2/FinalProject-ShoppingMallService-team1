package kr.co.lion.finalproject_shoppingmallservice_team1

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Base64
import kotlin.concurrent.thread

class Tools {
    companion object{

        // 뷰에 포커스를 주고 키보드를 올린다.
        fun showSoftInput(context: Context, view: View){
            // 뷰에 포커스를 준다.
            view.requestFocus()
            thread {
                // 딜레이
                SystemClock.sleep(200)
                // 키보드 관리 객체를 가져온다.
                val inputMethodManger = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                // 키보드를 올린다.
                inputMethodManger.showSoftInput(view, 0)
            }
        }

        // 키보드를 내려주고 포커스를 제거한다.
        fun hideSoftInput(activity: Activity){
            // 포커스를 가지고 있는 뷰가 있다면..
            if(activity.window.currentFocus != null){
                // 키보드 관리 객체를 가져온다.
                val inputMethodManger = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                // 키보드를 내려준다.
                inputMethodManger.hideSoftInputFromWindow(activity.window.currentFocus?.windowToken, 0)
                // 포커스를 제거해준다.
                activity.window.currentFocus?.clearFocus()
            }
        }

        // 입력 요소가 비어있을때 보여줄 다이얼로그를 구성하는 메서드
        fun showErrorDialog(context: Context, view: View, title:String, message:String){
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle(title)
            materialAlertDialogBuilder.setMessage(message)
            materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                showSoftInput(context, view)
            }
            materialAlertDialogBuilder.show()
        }

        // 촬영된 사진이 저장될 경로를 구해서 반환하는 메서드
        // authorities : AndroidManifest.xml에 등록한 File Provider의 이름
        fun getPictureUri(context:Context, authorities:String): Uri {
            // 촬영한 사진이 저장될 경로
            // 외부 저장소 중에 애플리케이션 영역 경로를 가져온다.
            val rootPath = context.getExternalFilesDir(null).toString()
            // 이미지 파일명을 포함한 경로
            val picPath = "${rootPath}/tempImage.jpg"
            // File 객체 생성
            val file = File(picPath)
            // 사진이 저장된 위치를 관리할 Uri 생성
            val contentUri = FileProvider.getUriForFile(context, authorities, file)

            return contentUri
        }


        // 카메라, 앨범 공통
        // 사진의 회전 각도값을 반환하는 메서드
        // ExifInterface : 사진, 영상, 소리 등의 파일에 기록한 정보
        // 위치, 날짜, 조리개값, 노출 정도 등등 다양한 정보가 기록된다.
        // ExifInterface 정보에서 사진 회전 각도값을 가져와서 그만큼 다시 돌려준다.
        fun getDegree(context:Context, uri: Uri) : Int {
            // 사진 정보를 가지고 있는 객체 가져온다.
            var exifInterface: ExifInterface? = null


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                // 이미지 데이터를 가져올 수 있는 Content Provide의 Uri를 추출한다.
                // val photoUri = MediaStore.setRequireOriginal(uri)
                // ExifInterface 정보를 읽어올 스트림을 추출한다.

                val inputStream = context.contentResolver.openInputStream(uri)!!
                // ExifInterface 객체를 생성한다.
                exifInterface = ExifInterface(inputStream)
            } else {
                // ExifInterface 객체를 생성한다.
                exifInterface = ExifInterface(uri.path!!)
            }

            if(exifInterface != null){
                // 반환할 각도값을 담을 변수
                var degree = 0
                // ExifInterface 객체에서 회전 각도값을 가져온다.
                val ori = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)

                degree = when(ori){
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }
                return degree
            }
            return 0
        }


        // 회전시키는 메서드
        fun rotateBitmap(bitmap: Bitmap, degree:Float): Bitmap {
            // 회전 이미지를 생성하기 위한 변환 행렬
            val matrix = Matrix()
            matrix.postRotate(degree)

            // 회전 행렬을 적용하여 회전된 이미지를 생성한다.
            // 첫 번째 : 원본 이미지
            // 두 번째와 세번째 : 원본 이미지에서 사용할 부분의 좌측 상단 x, y 좌표
            // 네번째와 다섯번째 : 원본 이미지에서 사용할 부분의 가로 세로 길이
            // 여기에서는 이미지데이터 전체를 사용할 것이기 때문에 전체 영역으로 잡아준다.
            // 여섯번째 : 변환행렬. 적용해준 변환행렬이 무엇이냐에 따라 이미지 변형 방식이 달라진다.
            val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)

            return rotateBitmap
        }

        // 이미지 사이즈를 조정하는 메서드
        fun resizeBitmap(bitmap: Bitmap, targetWidth:Int): Bitmap {
            // 이미지의 확대/축소 비율을 구한다.
            val ratio = targetWidth.toDouble() / bitmap.width.toDouble()
            // 세로 길이를 구한다.
            val targetHeight = (bitmap.height * ratio).toInt()
            // 크기를 조장한 Bitmap을 생성한다.
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)

            return resizedBitmap
        }

        // 이미지 뷰의 이미지를 추출해 로컬에 저장한다.
        fun saveImageViewData(context: Context, imageView: ImageView, fileName:String){
            // 외부 저장소까지의 경로를 가져온다.
            val filePath = context.getExternalFilesDir(null).toString()
            // 이미지 뷰에서 BitmapDrawable 객체를 추출한다.
            val bitmapDrawable = imageView.drawable as BitmapDrawable

            // 로컬에 저장할 경로
            val file = File("${filePath}/${fileName}")
            // 스트림 추출
            val fileOutputStream = FileOutputStream(file)
            // 이미지 저장
            // 첫 번째 : 이미지 데이터 포멧(JPEG, PNG, WEBP_LOSSLESS(무손실), WEBP_LOSSY(손실))
            // 두 번째 : 이미지의 퀄리티 (현재 100%)
            // 세 번째 : 이미지 데이터를 저장할 파일과 연결된 '스트림'
            bitmapDrawable.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        }

        // Bitmap -> String
        @RequiresApi(Build.VERSION_CODES.O)
        fun bitmapToString(bitmap: Bitmap): String {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            val bytes = stream.toByteArray()

            return Base64.getEncoder().encodeToString(bytes)
        }

        // String -> Bitmap
        @RequiresApi(Build.VERSION_CODES.O)
        fun stringToBitmap(string: String): Bitmap? {
            try {
                val decodedBytes: ByteArray = Base64.getDecoder().decode(string)
                return BitmapFactory.decodeStream(ByteArrayInputStream(decodedBytes))
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    }
}

enum class LOGIN_FRAGMENT_NAME(var num:Int, var str:String){

    LOGIN_FRAGMENT(0, "로그인"),
    LOGIN_SIGNUP_FRAGMENT(1, "회원가입"),
}

enum class LOGIN_SIGNUP_FRAGMENT_NAME(var num:Int, var str:String){

    LOGIN_SIGNUP1_FRAGMENT(0, "회원가입1"),
    LOGIN_SIGNUP2_FRAGMENT(1, "회원가입2"),
}

enum class NAVIGATION_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_FRAGMENT(0, "홈"),
    CENTER_FRAGMENT(1, "운동 센터"),
    TRAINER_FRAGMENT(2, "트레이너"),
    COMMUNITY_FRAGMENT(3, "커뮤니티"),
    MY_FRAGMENT(4, "MY"),
    TRAINER_POST_FRAGMENT(5, "게시글 생성")
}

enum class HOME_BOTTOM_FRAGMENT_NAME(var num:Int, var str:String){

    HOME_ADDRESS_BOTTOM_MAP_FRAGMENT(0, "현재 위치")
}

enum class HOME_SHOP_FRAGMENT_NAME(var num:Int, var str:String){

    SHOP_CONTAIN_FRAGMENT(0, "장바구니담기")
}

enum class CENTER_FRAGMENT_NAME(var num:Int, var str:String){

    READ_CENTER_FRAGMENT(0,"ReadCenterFragment"),
    READ_CENTER_TAB1_FRAGMENT(1, "ReadCenterTab1Fragment"),
    READ_CENTER_TAB2_FRAGMENT(2, "ReadCenterTab2Fragment"),
    READ_CENTER_TAB3_FRAGMENT(3, "ReadCenterTab3Fragment"),
}

enum class CENTER_TAB_NAME(var num: Int, var str: String){

    CENTER_TAB1_FRAGMENT(0, "헬스"),
    CENTER_TAB2_FRAGMENT(1, "필라테스"),
    CENTER_TAB3_FRAGMENT(2, "수영"),
}

enum class TRAINER_FRAGMENT_NAME(var num:Int, var str:String){

    READ_TRAINER_FRAGMENT(0, "ReadTrainerFragment"),
    READ_TRAINER_TAB1_FRAGMENT(1, "ReadTrainerTab1Fragment"),
    READ_TRAINER_TAB2_FRAGMENT(2, "ReadTrainerTab2Fragment"),
    READ_TRAINER_TAB3_FRAGMENT(3, "ReadTrainerTab3Fragment"),
}

// 트레이너 게시글 타입을 나타내는 값을 정의한다.
enum class TRAINER_POST_TYPE(var number: Int, var str:String){
    TRAINER_TYPE_FITNESS(0, "헬스"),
    TRAINER_TYPE_PILATES(1, "필라테스"),
    TRAINER_TYPE_SWIMMING(2, "수영"),
}

enum class POST_STATUS(var number: Int, var str:String){
    POST_STATUS_NORMAL(0, "활성"),
    POST_STATUS_DELETE(1, "삭제"),
}

enum class CONSULTING_FRAGMENT_NAME(var num: Int, var str:String){

    CONSULTING_CALENDAR_FRAGMENT(0, "ConsultingCalendarFragment"),
    CONSULTING_A(1, "A")
}

enum class COMMUNITY_FRAGMENT_NAME(var num:Int, var str:String){

    COMMUNITY_MYCONTENT_FRAGMENT(0, "CommunityMyContentFragment"),
    COMMUNITY_COMMENTCONTENT_FRAGMENT(1, "CommunityCommentContentFragment"),
    COMMUNITY_LIKECONTENT_FRAGMENT(2, "CommunityLikeContentFragment")
}

enum class MY_FRAGMENT_NAME(var num:Int, var str:String){

    MY_PROFILE_FRAGMENT(0, "프로필"),
    MY_POINT_FRAGMENT(1, "포인트"),
    MY_COUPON_FRAGMENT(2, "쿠폰"),
    MY_MEMBERSHIP_FRAGMENT(3, "회원권"),
    MY_REVIEW_FRAGMENT(4, "리뷰 관리"),
    MY_PICK_FRAGMENT(5, "찜"),
    MY_VISIT_CONSULTATION_FRAGMENT(6, "방문상담 신청 내역"),
    MY_PAYMENT_FRAGMENT(7, "결제 내역"),
    // 고객센터 프래그먼트
    MY_FAQ_FRAGMENT(9, "FAQ"),
    MY_SETTING_FRAGMENT(10, "설정"),
}

enum class MY_REVIEW_TAB_NAME(var num: Int, var str: String){

    MY_REVIEW_TAB1_FRAGMENT(0, "체육시설 리뷰"),
    MY_REVIEW_TAB2_FRAGMENT(1, "트레이너 리뷰"),
}

enum class MY_PICK_TAB_NAME(var num: Int, var str: String) {

    MY_PICK_TAB1_FRAGMENT(0, "트레이너 찜"),
    MY_PICK_TAB2_FRAGMENT(1, "체육시설 찜"),
}
enum class TRANSFER_MEMBERSHIP_FRAGMENT_NAME(var num:Int, var str:String){
    TRANSFER_MEMBERSHIP_CONTENT_LIST_FRAGMENT(0, "양도회원권 글 목록"),
    TRANSFER_MEMBERSHIP_DETAIL_CONTENT_FRAGMENT(1, "양도회원권 상세 페이지"),
    TRANSFER_MEMBERSHIP_CREATE_CONTENT_FRAGMENT(2, "양도회원권 글 작성"),
    TRANSFER_MEMBERSHIP_EDIT_CONTENT_FRAGMENT(3, "양도회원권 글 수정"),
}