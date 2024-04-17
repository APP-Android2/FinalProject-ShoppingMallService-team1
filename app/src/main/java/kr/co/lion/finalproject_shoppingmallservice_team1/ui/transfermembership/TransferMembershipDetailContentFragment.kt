package kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.TRANSFER_MEMBERSHIP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTransferMembershipDetailContentBinding

class TransferMembershipDetailContentFragment : Fragment() {

    private lateinit var fragmentTransferMembershipDetailContentBinding: FragmentTransferMembershipDetailContentBinding
    private lateinit var transferMembershipActivity: TransferMembershipActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentTransferMembershipDetailContentBinding = FragmentTransferMembershipDetailContentBinding.inflate(inflater)
        transferMembershipActivity = activity as TransferMembershipActivity

        settingToolbar()

        seeMembershipInfo()
        buyTransferMembership()
        putShoppingBoxTransferMembership()

        return fragmentTransferMembershipDetailContentBinding.root
    }

    private fun settingToolbar(){
        fragmentTransferMembershipDetailContentBinding.transfermembershipdetailcontentToolbar.apply {

            // 뒤로 가기
            setNavigationOnClickListener {
                transferMembershipActivity.removeFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_DETAIL_CONTENT_FRAGMENT)
            }

            // 작성자인 경우
            inflateMenu(R.menu.menu_transfermembership_detailcontent_writer)
            setOnMenuItemClickListener {
                when (it.itemId) {

                    // 수정하기
                    R.id.transfermembershipdetailcontent_menuItem_edit -> {
                        transferMembershipActivity.replaceFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_EDIT_CONTENT_FRAGMENT, true, true, null)
                    }

                    // 삭제하기
                    R.id.transfermembershipdetailcontent_menuItem_delete -> {

                        MaterialAlertDialogBuilder(transferMembershipActivity, R.style.MyDialogTheme).apply {
                            setTitle("게시물 삭제")
                            setMessage("이 게시물을 삭제하시겠습니까?")
                            setNegativeButton("취소", null)
                            setPositiveButton("삭제", null)
                            show().apply {
                                getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                                getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
                            }
                        }
                    }
                }
                true
            }

//            // 작성자가 아닌 경우
//            inflateMenu(R.menu.menu_transfermembership_detailcontent_reader)
//            setOnMenuItemClickListener {
//                when (it.itemId) {
//
//                    // 메시지 보내기
//                    R.id.transfermembershipdetailcontent_menuItem_message -> {}
//
//                    // 신고하기
//                    R.id.transfermembershipdetailcontent_menuItem_report -> {}
//                }
//                true
//            }
        }
    }

    // 운동 시설 ㅣ 트레이너 (회원권 정보)보러가기
    private fun seeMembershipInfo(){
        fragmentTransferMembershipDetailContentBinding.transfermembershipdetailcontentMembershipInfoButton.setOnClickListener {

        }
    }

    // 구매하기
    private fun buyTransferMembership(){
        fragmentTransferMembershipDetailContentBinding.transfermembershipdetailcontentBuyButton.setOnClickListener {

        }
    }

    // 장바구니에 담기
    private fun putShoppingBoxTransferMembership(){
        fragmentTransferMembershipDetailContentBinding.transfermembershipdetailcontentPutshoppingboxButton.setOnClickListener {

        }
    }
}