package kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.TRANSFER_MEMBERSHIP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTransferMembershipCreateContentBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership.viewmodel.TransferMembershipCreateContentViewModel

class TransferMembershipCreateContentFragment : Fragment() {

    private lateinit var fragmentTransferMembershipCreateContentBinding: FragmentTransferMembershipCreateContentBinding
    private lateinit var transferMembershipActivity: TransferMembershipActivity
    private val transferMembershipCreateContentViewModel: TransferMembershipCreateContentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentTransferMembershipCreateContentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transfer_membership_create_content, container, false)
        fragmentTransferMembershipCreateContentBinding.transferMembershipCreateContentViewModel = transferMembershipCreateContentViewModel
        fragmentTransferMembershipCreateContentBinding.lifecycleOwner = this@TransferMembershipCreateContentFragment

        transferMembershipActivity = activity as TransferMembershipActivity

        settingToolbar()
        chooseMembership()
        showHoldingMembershipBottomSheet()

        return fragmentTransferMembershipCreateContentBinding.root
    }

    private fun settingToolbar(){
        fragmentTransferMembershipCreateContentBinding.transfermembershipCreateToolbar.apply {

            setNavigationOnClickListener {
                transferMembershipActivity.removeFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_CREATE_CONTENT_FRAGMENT)
            }

            inflateMenu(R.menu.menu_transfermembership_create)
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.transfermembership_menuItem_create -> {

                        settingProgressbar()

                        // 서버에 글이 올라가면 나가기
                        transferMembershipActivity.removeFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_CREATE_CONTENT_FRAGMENT)
                    }
                }
                true
            }
        }
    }

    fun settingProgressbar(){
        fragmentTransferMembershipCreateContentBinding.transfermembershipCreateProgressBar.apply {
            isIndeterminate = true
            isVisible = true
        }
    }

    private fun showHoldingMembershipBottomSheet(){
        val transferMembershipHoldingMembershipBottomFragment = TransferMembershipHoldingMembershipBottomFragment()
        transferMembershipHoldingMembershipBottomFragment.show(transferMembershipActivity.supportFragmentManager, "HoldingMembershipBottomSheet")
    }

    fun chooseMembership(){
        fragmentTransferMembershipCreateContentBinding.transfermembershipCreateChooseMembershipButton.setOnClickListener {
            showHoldingMembershipBottomSheet()
        }
    }
}