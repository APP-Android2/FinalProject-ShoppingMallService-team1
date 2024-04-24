package kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership

import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTransferMembershipHoldingMembershipBottomBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowHoldingmembershipItemBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership.viewmodel.TransferMembershipHoldingMembershipViewModel

class TransferMembershipHoldingMembershipBottomFragment : BottomSheetDialogFragment() {

    private lateinit var fragmentTransferMembershipHoldingBottomBinding: FragmentTransferMembershipHoldingMembershipBottomBinding
    private lateinit var transferMembershipActivity: TransferMembershipActivity

    private val transferMembershipHoldingMembershipViewModel: TransferMembershipHoldingMembershipViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentTransferMembershipHoldingBottomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transfer_membership_holding_membership_bottom, container, false)
        fragmentTransferMembershipHoldingBottomBinding.transferMembershipHoldingMembershipViewModel = transferMembershipHoldingMembershipViewModel
        fragmentTransferMembershipHoldingBottomBinding.lifecycleOwner = this@TransferMembershipHoldingMembershipBottomFragment

        transferMembershipActivity = activity as TransferMembershipActivity

        settingRecyclerView()

        return fragmentTransferMembershipHoldingBottomBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val screenHeight = resources.displayMetrics.heightPixels
        val maxExpandedScreenHeight = (screenHeight * 0.8).toInt()

        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = maxExpandedScreenHeight
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheet.layoutParams.height = maxExpandedScreenHeight
        }
        return dialog
    }

    private fun settingRecyclerView(){
        fragmentTransferMembershipHoldingBottomBinding.holdingmembershipRecyclerView.apply {

            adapter = HoldingMemberShipRecyclerViewAdapter()
            layoutManager = LinearLayoutManager(transferMembershipActivity)
        }
    }

    inner class HoldingMemberShipRecyclerViewAdapter : RecyclerView.Adapter<HoldingMemberShipRecyclerViewAdapter.HoldingMemberShipViewHolder>(){

        inner class HoldingMemberShipViewHolder(rowHoldingMembershipItemBinding: RowHoldingmembershipItemBinding) : RecyclerView.ViewHolder(rowHoldingMembershipItemBinding.root){

            val rowHoldingMembershipItemBinding: RowHoldingmembershipItemBinding

            init {

                this.rowHoldingMembershipItemBinding = rowHoldingMembershipItemBinding
                rowHoldingMembershipItemBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingMemberShipViewHolder {

            val rowHoldingMembershipItemBinding = RowHoldingmembershipItemBinding.inflate(layoutInflater)
            val holdingMemberShipViewHolder = HoldingMemberShipViewHolder(rowHoldingMembershipItemBinding)

            return holdingMemberShipViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: HoldingMemberShipViewHolder, position: Int) {

            holder.rowHoldingMembershipItemBinding.holdingmembershipItemCardView.setOnClickListener {

                SystemClock.sleep(200)

                dismiss()
            }

            holder.rowHoldingMembershipItemBinding.holdingmembershipItemButton.setOnClickListener {

                SystemClock.sleep(200)

                dismiss()
            }
        }
    }
}