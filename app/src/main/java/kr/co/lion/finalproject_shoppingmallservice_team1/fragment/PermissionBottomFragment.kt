package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.finalproject_shoppingmallservice_team1.MainActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentPermissionBottomBinding

class PermissionBottomFragment : BottomSheetDialogFragment() {

    private lateinit var fragmentPermissionBottomBinding: FragmentPermissionBottomBinding
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentPermissionBottomBinding = FragmentPermissionBottomBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        permissionCheckButton()

        return fragmentPermissionBottomBinding.root
    }

    private fun permissionCheckButton(){

        fragmentPermissionBottomBinding.PermissionButton.setOnClickListener {

            // 권한 설정
        }
    }

    // 다이얼로그가 만들어질 때 자동으로 호출되는 메서드
    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {

        // 다이얼로그를 받는다.
        val dialog = super.onCreateDialog(savedInstanceState)

        // 다이얼로그가 보일때 동작하는 리스너
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog

            // 높이를 설정한다.
            setBottomSheetHeight(bottomSheetDialog)
        }

        return dialog
    }

    // BottomSheet의 높이를 설정해준다.
    private fun setBottomSheetHeight(bottomSheetDialog: BottomSheetDialog){

        // BottomSheet의 기본 뷰 객체를 가져온다.
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!

        // BottomSheet 높이를 설정할 수 있는 객체를 생성한다.
        val behavior = BottomSheetBehavior.from(bottomSheet)

        // 높이를 설정한다.
        bottomSheet.layoutParams.height = getBottomSheetDialogHeight()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

    }

    // BottomSheet의 높이를 구한다(화면 액정의 90% 크기)
    private fun getBottomSheetDialogHeight() : Int{
        return (getWindowHeight() * 0.90f).toInt()
    }

    // 사용자 단말기 액정의 길이를 구해 반환
    private fun getWindowHeight() : Int {

        // 화면 크기 정보를 담을 배열 객체
        val displayMetrics = DisplayMetrics()

        // 액정의 가로 세로 길이 정보를 담아준다.
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)

        // 세로길이를 반환해준다.
        return displayMetrics.heightPixels
    }
}