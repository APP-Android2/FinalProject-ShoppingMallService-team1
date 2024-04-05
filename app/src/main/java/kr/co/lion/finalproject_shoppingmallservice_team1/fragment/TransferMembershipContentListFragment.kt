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
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTransferMembershipContentListBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowTransfermembershipItemBinding

class TransferMembershipContentListFragment : Fragment() {

    private lateinit var fragmentTransferMembershipContentListBinding: FragmentTransferMembershipContentListBinding
    private lateinit var transferMembershipActivity: TransferMembershipActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentTransferMembershipContentListBinding = FragmentTransferMembershipContentListBinding.inflate(inflater)
        transferMembershipActivity = activity as TransferMembershipActivity

        settingToolbar()
        settingRecyclerView()
        startCreateContent()

        return fragmentTransferMembershipContentListBinding.root
    }

    fun settingToolbar(){

        fragmentTransferMembershipContentListBinding.transfermembershipToolbar.apply {

            // 뒤로 가기
            setNavigationOnClickListener {
                transferMembershipActivity.finish()
            }

            inflateMenu(R.menu.menu_transfermembership)
            setOnMenuItemClickListener {
                when(it.itemId){

                    // 장바구니 화면 이동
                    R.id.transfermembership_menuItem_shoppingCart -> {}
                }

                true
            }
        }
    }

    fun settingRecyclerView(){
        fragmentTransferMembershipContentListBinding.transfermembershipRecyclerView.apply {

            adapter = TransferMembershipRecyclerViewAdapter()
            layoutManager = LinearLayoutManager(transferMembershipActivity)
            val deco = MaterialDividerItemDecoration(transferMembershipActivity, MaterialDividerItemDecoration.VERTICAL)
            addItemDecoration(deco)
        }
    }

    // 글 작성 화면 이동
    fun startCreateContent(){
        fragmentTransferMembershipContentListBinding.transfermembershipCreateButton.setOnClickListener {
            transferMembershipActivity.replaceFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_CREATE_CONTENT_FRAGMENT, true, true, null)
        }
    }

    inner class TransferMembershipRecyclerViewAdapter : RecyclerView.Adapter<TransferMembershipRecyclerViewAdapter.TransferMembershipViewHolder>() {

        inner class TransferMembershipViewHolder(rowTransferMembershipItemBinding: RowTransfermembershipItemBinding) : RecyclerView.ViewHolder(rowTransferMembershipItemBinding.root){

            val rowTransferMembershipItemBinding: RowTransfermembershipItemBinding

            init {
                this.rowTransferMembershipItemBinding = rowTransferMembershipItemBinding
                rowTransferMembershipItemBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferMembershipViewHolder {
            val rowTransferMembershipItemBinding = RowTransfermembershipItemBinding.inflate(layoutInflater)
            val transferMembershipViewHolder = TransferMembershipViewHolder(rowTransferMembershipItemBinding)
            return transferMembershipViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: TransferMembershipViewHolder, position: Int) {

            // 글 상세 화면 이동
            holder.rowTransferMembershipItemBinding.rowtransfermembershipContent.setOnClickListener {
                transferMembershipActivity.replaceFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_DETAIL_CONTENT_FRAGMENT, true, true, null)
            }
        }
    }
}