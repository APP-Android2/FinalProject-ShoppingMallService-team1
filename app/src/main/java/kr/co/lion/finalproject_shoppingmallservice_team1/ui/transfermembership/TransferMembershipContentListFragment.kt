package kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership

import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.TRANSFER_MEMBERSHIP_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTransferMembershipContentListBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowTransfermembershipItemBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.model.Center
import kr.co.lion.finalproject_shoppingmallservice_team1.model.FitnessCenterMembership
import kr.co.lion.finalproject_shoppingmallservice_team1.model.MembershipType
import kr.co.lion.finalproject_shoppingmallservice_team1.model.PTMembership
import kr.co.lion.finalproject_shoppingmallservice_team1.model.TransferMembershipPost
import kr.co.lion.finalproject_shoppingmallservice_team1.model.User
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.transfermembership.viewmodel.TransferMembershipContentListViewModel

class TransferMembershipContentListFragment : Fragment() {

    private lateinit var fragmentTransferMembershipContentListBinding: FragmentTransferMembershipContentListBinding
    private lateinit var transferMembershipActivity: TransferMembershipActivity
    private val transferMembershipContentListViewModel: TransferMembershipContentListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentTransferMembershipContentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transfer_membership_content_list, container, false)
        fragmentTransferMembershipContentListBinding.transferMembershipContentListViewModel = transferMembershipContentListViewModel
        fragmentTransferMembershipContentListBinding.lifecycleOwner = this@TransferMembershipContentListFragment

        transferMembershipActivity = activity as TransferMembershipActivity

        transferMembershipContentListViewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            fragmentTransferMembershipContentListBinding.transfermembershipRecyclerView.adapter = TransferMembershipRecyclerViewAdapter(posts)
        })

        settingToolbar()

        chip1()
        chip2()

        settingRecyclerView()
        startCreateContent()

        return fragmentTransferMembershipContentListBinding.root
    }

    private fun settingToolbar(){

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

    private fun chip1() {
        fragmentTransferMembershipContentListBinding.transfermembershipcontentlistChip1.apply {
            setOnClickListener {

                val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)

                val popup = PopupMenu(contextWrapper, this)
                popup.inflate(R.menu.menu_transfermembership_chip1)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        // 거리순
                        R.id.transfermembership_menuItem_distance -> {
                            text = "거리순"
                        }
                        // 최신순
                        R.id.transfermembership_menuItem_latest -> {
                            text = "최신순"
                        }
                    }
                    true
                }

                popup.show()
            }
        }
    }


    private fun chip2(){
        fragmentTransferMembershipContentListBinding.transfermembershipcontentlistChip2.apply {
            setOnClickListener {
                val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)
                val popup = PopupMenu(contextWrapper, this)
                popup.inflate(R.menu.menu_transfermembership_chip2)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.transfermembership_menuItem_total -> {
                            text = "모든 운동"
                        }
                        R.id.transfermembership_menuItem_health -> {
                            text = "헬스"
                        }
                        R.id.transfermembership_menuItem_pilates -> {
                            text = "필라테스"
                        }
                        R.id.transfermembership_menuItem_swimming -> {
                            text = "수영"
                        }
                    }
                    true
                }
            }
        }
    }

    private fun settingRecyclerView(){
        fragmentTransferMembershipContentListBinding.transfermembershipRecyclerView.apply {

            transferMembershipContentListViewModel.loadPosts()
            val postList = transferMembershipContentListViewModel.posts.value ?: emptyList()

            adapter = TransferMembershipRecyclerViewAdapter(postList)
            layoutManager = LinearLayoutManager(transferMembershipActivity)
            val deco = MaterialDividerItemDecoration(transferMembershipActivity, MaterialDividerItemDecoration.VERTICAL)
            addItemDecoration(deco)
        }
    }

    // 글 작성 화면 이동
    private fun startCreateContent(){
        fragmentTransferMembershipContentListBinding.transfermembershipCreateButton.setOnClickListener {
            transferMembershipActivity.replaceFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_CREATE_CONTENT_FRAGMENT, true, true, null)
        }
    }

    inner class TransferMembershipRecyclerViewAdapter(private val items: List<TransferMembershipPost>) : RecyclerView.Adapter<TransferMembershipRecyclerViewAdapter.TransferMembershipViewHolder>() {

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
            return items.size
        }

        override fun onBindViewHolder(holder: TransferMembershipViewHolder, position: Int) {

            val post = items[position]

            CoroutineScope(Dispatchers.IO).launch {

                val db = FirebaseFirestore.getInstance()
                val user = db.collection("users").document(post.userId).get().await().toObject<User?>()
                Log.d("test1234", "user 정보 가져오기 성공")
                val membershipDocument = db.collection("Memberships").document(post.membershipId).get().await()
                val membershipType = membershipDocument.get("membershipType") as String
                Log.d("test1234", "membership 정보 가져오기 성공")
                val centerId = membershipDocument.get("centerId") as String
                val center = db.collection("Center").document(centerId).get().await().toObject<Center?>()

                withContext(Dispatchers.Main) {
                    when (membershipType) {
                        MembershipType.FITNESS_CENTER.toString() -> {
                            val membership = membershipDocument.toObject<FitnessCenterMembership>()
                            holder.rowTransferMembershipItemBinding.apply {
                                rowtransfermembershipNickname.text = user?.nickName // 닉네임

                                rowtransfermembershipTime.text = post.postTime // 시간

                                rowtransfermembershipCenterTrainerName.text = membership?.name // 회원권 이름
                                rowtransfermembershipRemain.text = membership?.endDate // 남은 기간

                                rowtransfermembershipSportname.text = post.exerciseType // 운동 종류
                                rowtransfermembershipAddress.text = center?.centerLocation // 주소
                                rowtransfermembershipDistance.text = "거리" // 거리

                                rowtransfermembershipPrice.text = "${post.price}원" // 가격
                            }
                        }

                        MembershipType.PT.toString() -> {
                            val membership = membershipDocument.toObject<PTMembership>()
                            holder.rowTransferMembershipItemBinding.apply {
                                rowtransfermembershipNickname.text = user?.nickName // 닉네임

                                rowtransfermembershipTime.text = post.postTime // 시간

                                rowtransfermembershipCenterTrainerName.text = membership?.name // 회원권 이름
                                rowtransfermembershipRemain.text = "${membership?.count}회" // 남은 횟수

                                rowtransfermembershipSportname.text = post.exerciseType // 운동 종류
                                rowtransfermembershipAddress.text = center?.centerLocation // 주소
                                rowtransfermembershipDistance.text = "거리" // 거리

                                rowtransfermembershipPrice.text = "${post.price}원" // 가격
                            }
                        }

                        else -> throw IllegalArgumentException("Unknown Membership Type")
                    }

                    // 글 상세 화면 이동
                    holder.rowTransferMembershipItemBinding.rowtransfermembershipContent.setOnClickListener {
                        transferMembershipActivity.replaceFragment(TRANSFER_MEMBERSHIP_FRAGMENT_NAME.TRANSFER_MEMBERSHIP_DETAIL_CONTENT_FRAGMENT, true, true, null)
                    }
                }
            }
        }
    }
}