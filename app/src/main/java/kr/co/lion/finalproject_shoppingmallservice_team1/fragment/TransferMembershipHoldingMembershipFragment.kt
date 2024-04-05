package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.TRANSFER_MEMBERSHIP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.TransferMembershipActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTransferMembershipHoldingMembershipBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowHoldingmembershipItemBinding

class TransferMembershipHoldingMembershipFragment : Fragment() {

    private lateinit var fragmentTransferMembershipHoldingMembershipBinding: FragmentTransferMembershipHoldingMembershipBinding
    private lateinit var transferMembershipActivity: TransferMembershipActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentTransferMembershipHoldingMembershipBinding = FragmentTransferMembershipHoldingMembershipBinding.inflate(inflater)
        transferMembershipActivity = activity as TransferMembershipActivity

        settingToolbar()
        settingRecyclerView()

        return fragmentTransferMembershipHoldingMembershipBinding.root
    }

    private fun settingToolbar(){
        fragmentTransferMembershipHoldingMembershipBinding.transfermembershipholdingmembershipToolbar.apply {

            setNavigationOnClickListener {
                transferMembershipActivity.removeFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_HOLDING_FRAGMENT)
            }
        }
    }

    private fun settingRecyclerView(){
        fragmentTransferMembershipHoldingMembershipBinding.transfermembershipholdingmembershipRecyclerView.apply {

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
                transferMembershipActivity.removeFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_HOLDING_FRAGMENT)
            }

            holder.rowHoldingMembershipItemBinding.holdingmembershipItemButton.setOnClickListener {
                transferMembershipActivity.removeFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_HOLDING_FRAGMENT)
            }
        }
    }
}