package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerPurchasingBottomBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity

class ReadTrainerPurchasingBottomFragment : BottomSheetDialogFragment() {

    lateinit var fragmentReadTrainerPurchasingBottomBinding: FragmentReadTrainerPurchasingBottomBinding
    lateinit var readTrainerActivity: ReadTrainerActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentReadTrainerPurchasingBottomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_trainer_purchasing_bottom, container, false)
        readTrainerActivity = activity as ReadTrainerActivity

        settingTrainerBuyButtonClick()
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

    // BottomSheet의 높이를 구한다(화면 액정의 60% 크기)
    fun getBottomSheetDialogHeight() : Int {
        return (getWindowHeight() * 0.6).toInt()
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
    fun settingTrainerBuyButtonClick(){
        fragmentReadTrainerPurchasingBottomBinding.apply {
            trainerBuyButton.apply {
                setOnClickListener {
                    val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context, R.style.MyDialogTheme)
                    materialAlertDialogBuilder.setTitle("결제")
                    materialAlertDialogBuilder.setMessage("결제 완료했습니다.")
                    // 확인 버튼 누르면  다시 NavigationActivity로 돌아감
                    materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        val intent = Intent(readTrainerActivity,  NavigationActivity::class.java)
                        startActivity(intent)
                        readTrainerActivity.finish()
                    }

                    val alertDialog = materialAlertDialogBuilder.create()
                    alertDialog.setOnShowListener {
                        val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        positiveButton.setTextColor(ContextCompat.getColor(context, R.color.black))
                    }
                    alertDialog.show()
                }
            }
        }
    }


    fun bottomDownButtonClick(){
        fragmentReadTrainerPurchasingBottomBinding.apply {
            // 기본 상태.
            materialCardViewOption2.visibility = View.GONE
            optionList2.visibility = View.GONE
            optionList3.visibility = View.GONE

            // 닫혀있는 "옵션 선택" 바를 클릭 시.
            trainerOptionClickLayout1.setOnClickListener {
                materialCardViewOption1.visibility = View.GONE
                materialCardViewOption2.visibility = View.VISIBLE
            }
            readTrainerOptionDownImageButton1.setOnClickListener {
                materialCardViewOption1.visibility = View.GONE
                materialCardViewOption2.visibility = View.VISIBLE
            }

            // 펼쳐져 있는 "옵션 선택" 바를 클릭 시.
            trainerOptionClickLayout2.setOnClickListener {
                materialCardViewOption1.visibility = View.VISIBLE
                materialCardViewOption2.visibility = View.GONE
            }
            readTrainerOptionDownImageButton2.setOnClickListener {
                materialCardViewOption1.visibility = View.VISIBLE
                materialCardViewOption2.visibility = View.GONE
            }

            // 선택되어 있는 상품을 삭제 할때.
            readTrainerItemCloseImageButton.setOnClickListener {
                optionList2.visibility = View.GONE
                optionList3.visibility = View.GONE
            }

            // 1번째 옵션 선택 시, 해당 물건에 대한 상품 가격
            textViewOptionItem1.setOnClickListener {
                optionList2.visibility = View.VISIBLE
                optionList3.visibility = View.VISIBLE
                itemPrice01.visibility = View.VISIBLE
                itemPrice02.visibility = View.GONE
                itemPrice03.visibility = View.GONE
                materialCardViewOption1.visibility = View.VISIBLE
                materialCardViewOption2.visibility = View.GONE

                resultItem01.visibility = View.VISIBLE
                resultItemPrice01.visibility = View.VISIBLE
                resultItem02.visibility = View.GONE
                resultItemPrice02.visibility = View.GONE
                resultItem03.visibility = View.GONE
                resultItemPrice03.visibility = View.GONE
            }
            // 2번째 옵션 선택 시, 해당 물건에 대한 상품 가격
            textViewOptionItem2.setOnClickListener {
                optionList2.visibility = View.VISIBLE
                optionList3.visibility = View.VISIBLE
                itemPrice01.visibility = View.GONE
                itemPrice02.visibility = View.VISIBLE
                itemPrice03.visibility = View.GONE
                materialCardViewOption1.visibility = View.VISIBLE
                materialCardViewOption2.visibility = View.GONE

                resultItem01.visibility = View.GONE
                resultItemPrice01.visibility = View.GONE
                resultItem02.visibility = View.VISIBLE
                resultItemPrice02.visibility = View.VISIBLE
                resultItem03.visibility = View.GONE
                resultItemPrice03.visibility = View.GONE
            }
            // 3번째 옵션 선택 시, 해당 물건에 대한 상품 가격
            textViewOptionItem3.setOnClickListener {
                optionList2.visibility = View.VISIBLE
                optionList3.visibility = View.VISIBLE
                itemPrice01.visibility = View.GONE
                itemPrice02.visibility = View.GONE
                itemPrice03.visibility = View.VISIBLE
                materialCardViewOption1.visibility = View.VISIBLE
                materialCardViewOption2.visibility = View.GONE

                resultItem01.visibility = View.GONE
                resultItemPrice01.visibility = View.GONE
                resultItem02.visibility = View.GONE
                resultItemPrice02.visibility = View.GONE
                resultItem03.visibility = View.VISIBLE
                resultItemPrice03.visibility = View.VISIBLE
            }
        }
    }

    fun settingShoppingCartButtonClick(){
        fragmentReadTrainerPurchasingBottomBinding.apply {
            trainerShoppingCartButton.apply {
                setOnClickListener {
                    val colortrantBlack = ContextCompat.getColor(context, R.color.trantBlack)
                    val snackbar = Snackbar.make(it, "장바구니에 담겼습니다", Snackbar.LENGTH_SHORT)

                    snackbar.apply {
                        // 메시지 색상, 배경색, 애니매이션 종류
                        setTextColor(Color.WHITE)
                        snackbar.setBackgroundTint(colortrantBlack)
                        snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
                    }.show()
                }
            }
        }
    }
}