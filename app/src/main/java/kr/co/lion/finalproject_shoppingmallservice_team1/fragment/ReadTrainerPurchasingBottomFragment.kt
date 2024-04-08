package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.ReadTrainerActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerPurchasingBottomBinding

class ReadTrainerPurchasingBottomFragment : BottomSheetDialogFragment() {

    lateinit var fragmentReadTrainerPurchasingBottomBinding: FragmentReadTrainerPurchasingBottomBinding
    lateinit var readTrainerActivity: ReadTrainerActivity

    // 프래그먼트 객체를 담을 변수
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentReadTrainerPurchasingBottomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_trainer_purchasing_bottom, container, false)
        readTrainerActivity = activity as ReadTrainerActivity

        settingTrainerPurchasingButton2Click()
        bottomDownButtonClick()
        settingShoppingCartButtonClick()

        return fragmentReadTrainerPurchasingBottomBinding.root
    }

    // 다이얼로그가 만들어질 때 자동으로 호출되는 메서드
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
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
    fun setBottomSheetHeight(bottomSheetDialog: BottomSheetDialog){
        // BottomSheet의 기본 뷰 객체를 가져온다
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
        // BottomSheet 높이를 설정할 수 있는 객체를 생성한다.
        val behavior = BottomSheetBehavior.from(bottomSheet)
        // 높이를 설정한다.
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // BottomSheet의 높이를 구한다(화면 액정의 40% 크기)
    fun getBottomSheetDialogHeight() : Int {
        return (getWindowHeight() * 0.5).toInt()
    }

    // 사용자 단말기 액정의 길이를 구해 반환하는 메서드
    fun getWindowHeight() : Int {
        // 화면 크기 정보를 담을 배열 객체
        val displayMetrics = DisplayMetrics()
        // 액정의 가로 세로 길이 정보를 담아준다.
        readTrainerActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // 세로길이를 반환해준다.
        return displayMetrics.heightPixels
    }

    // 구매하기 버튼 이벤트
    fun settingTrainerPurchasingButton2Click(){
        fragmentReadTrainerPurchasingBottomBinding.apply {
            trainerPurchasingButton2.apply {
                setOnClickListener {
                    val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
                    materialAlertDialogBuilder.setTitle("결제")
                    materialAlertDialogBuilder.setMessage("결제 완료했습니다!")
                    // 확인 버튼 누르면  다시 NavigationActivity로 돌아감
                    materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        val intent = Intent(readTrainerActivity,  NavigationActivity::class.java)
                        startActivity(intent)
                        readTrainerActivity.finish()
                    }
                    materialAlertDialogBuilder.show()
                }
            }
        }
    }


    fun bottomDownButtonClick(){
        fragmentReadTrainerPurchasingBottomBinding.apply {
            materialCardViewOption2.visibility = View.GONE
            optionList2.visibility = View.GONE
            optionList3.visibility = View.GONE

            readTrainerOptionDownImageButton1.setOnClickListener {
                materialCardViewOption1.visibility = View.GONE
                materialCardViewOption2.visibility = View.VISIBLE

            }
            readTrainerOptionDownImageButton2.setOnClickListener {
                materialCardViewOption1.visibility = View.VISIBLE
                materialCardViewOption2.visibility = View.GONE
            }
            textViewOptionItem1.setOnClickListener {
                optionList2.visibility = View.VISIBLE
                optionList3.visibility = View.VISIBLE
                materialCardViewOption1.visibility = View.VISIBLE
                materialCardViewOption2.visibility = View.GONE
            }
            readTrainerItemCloseImageButton.setOnClickListener {
                optionList2.visibility = View.GONE
                optionList3.visibility = View.GONE
            }
        }
    }

    fun settingShoppingCartButtonClick(){
        fragmentReadTrainerPurchasingBottomBinding.apply {
            trainerShoppingCartButton.apply {
                setOnClickListener {
                    Toast.makeText(readTrainerActivity, "장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}