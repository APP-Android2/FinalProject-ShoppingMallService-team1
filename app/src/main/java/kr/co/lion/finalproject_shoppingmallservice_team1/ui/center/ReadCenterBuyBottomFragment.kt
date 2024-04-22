package kr.co.lion.finalproject_shoppingmallservice_team1.ui.center

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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadCenterBuyBottomBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity


class ReadCenterBuyBottomFragment : BottomSheetDialogFragment() {

    lateinit var fragmentReadCenterBuyBottomBinding: FragmentReadCenterBuyBottomBinding
    lateinit var readCenterActivity: ReadCenterActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadCenterBuyBottomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_center_buy_bottom, container, false)
        readCenterActivity = activity as ReadCenterActivity

        settingCenterBuyButtonClick()
        bottomDownButtonClick()
        settingShoppingCartButtonClick()


        return fragmentReadCenterBuyBottomBinding.root
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

    // BottomSheet의 높이를 구한다(화면 액정의 60% 크기)
    fun getBottomSheetDialogHeight() : Int {
        return (getWindowHeight() * 0.6).toInt()
    }

    // 사용자 단말기 액정의 길이를 구해 반환하는 메서드
    fun getWindowHeight() : Int {
        // 화면 크기 정보를 담을 배열 객체
        val displayMetrics = DisplayMetrics()
        // 액정의 가로 세로 길이 정보를 담아준다.
        readCenterActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // 세로길이를 반환해준다.
        return displayMetrics.heightPixels
    }


    // 구매하기 버튼 이벤트
    fun settingCenterBuyButtonClick(){
        fragmentReadCenterBuyBottomBinding.apply {
            centerBuyButton.apply {
                setOnClickListener {
                    val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
                    materialAlertDialogBuilder.setTitle("결제")
                    materialAlertDialogBuilder.setMessage("결제 완료했습니다!")
                    // 확인 버튼 누르면  다시 NavigationActivity로 돌아감
                    materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        val intent = Intent(readCenterActivity,  NavigationActivity::class.java)
                        startActivity(intent)
                        readCenterActivity.finish()
                    }
                    materialAlertDialogBuilder.show()
                }
            }
        }
    }


    fun bottomDownButtonClick(){
        fragmentReadCenterBuyBottomBinding.apply {
            cardViewCenterBuyOption2.visibility = View.GONE
            optionList2.visibility = View.GONE
            optionList3.visibility = View.GONE

            centerOptionClickLayout1.setOnClickListener {
                cardViewCenterBuyOption1.visibility = View.GONE
                cardViewCenterBuyOption2.visibility = View.VISIBLE
            }
            centerOptionDownImageButton1.setOnClickListener {
                cardViewCenterBuyOption1.visibility = View.GONE
                cardViewCenterBuyOption2.visibility = View.VISIBLE
            }


            centerOptionClickLayout2.setOnClickListener {
                cardViewCenterBuyOption1.visibility = View.VISIBLE
                cardViewCenterBuyOption2.visibility = View.GONE
            }
            centerOptionDownImageButton2.setOnClickListener {
                cardViewCenterBuyOption1.visibility = View.VISIBLE
                cardViewCenterBuyOption2.visibility = View.GONE
            }

            textViewOptionItem1.setOnClickListener {
                optionList2.visibility = View.VISIBLE
                optionList3.visibility = View.VISIBLE
                cardViewCenterBuyOption1.visibility = View.VISIBLE
                cardViewCenterBuyOption2.visibility = View.GONE
            }
            textViewOptionItem2.setOnClickListener {
                optionList2.visibility = View.VISIBLE
                optionList3.visibility = View.VISIBLE
                cardViewCenterBuyOption1.visibility = View.VISIBLE
                cardViewCenterBuyOption2.visibility = View.GONE
            }
            textViewOptionItem3.setOnClickListener {
                optionList2.visibility = View.VISIBLE
                optionList3.visibility = View.VISIBLE
                cardViewCenterBuyOption1.visibility = View.VISIBLE
                cardViewCenterBuyOption2.visibility = View.GONE
            }
            centerOptionItemCloseImageButton.setOnClickListener {
                optionList2.visibility = View.GONE
                optionList3.visibility = View.GONE
            }
        }
    }

    fun settingShoppingCartButtonClick(){
        fragmentReadCenterBuyBottomBinding.apply {
            centerShoppingCartButton.apply {
                setOnClickListener {
                    Toast.makeText(readCenterActivity, "장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}