package kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.TRANSFER_MEMBERSHIP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTransferMembershipEditContentBinding

class TransferMembershipEditContentFragment : Fragment() {

    private lateinit var fragmentTransferMembershipEditContentBinding: FragmentTransferMembershipEditContentBinding
    private lateinit var transferMembershipActivity: TransferMembershipActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentTransferMembershipEditContentBinding = FragmentTransferMembershipEditContentBinding.inflate(inflater)
        transferMembershipActivity = activity as TransferMembershipActivity

        settingToolbar()
        chooseMembership()

        return fragmentTransferMembershipEditContentBinding.root
    }

    private fun settingToolbar(){
        fragmentTransferMembershipEditContentBinding.transfermembershipEditToolbar.apply {

            setNavigationOnClickListener {
                transferMembershipActivity.removeFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_EDIT_CONTENT_FRAGMENT)
            }

            inflateMenu(R.menu.menu_transfermembership_create)
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.transfermembership_menuItem_create -> {

                        settingProgressbar()

                        // 서버에 글이 올라가면 나가기
                        transferMembershipActivity.removeFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_EDIT_CONTENT_FRAGMENT)
                    }
                }
                true
            }
        }
    }

    fun settingProgressbar(){
        fragmentTransferMembershipEditContentBinding.transfermembershipEditProgressBar.apply {
            isIndeterminate = true

        }
    }

    private fun showHoldingMembershipBottomSheet(){
        val transferMembershipHoldingMembershipBottomFragment = TransferMembershipHoldingMembershipBottomFragment()
        transferMembershipHoldingMembershipBottomFragment.show(transferMembershipActivity.supportFragmentManager, "HoldingMembershipBottomSheet")
    }

    fun chooseMembership(){
        fragmentTransferMembershipEditContentBinding.transfermembershipEditChooseMembershipButton.setOnClickListener {
            showHoldingMembershipBottomSheet()
        }
    }
}