package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer

import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerTab2Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowReadTrainerTab2Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.model.TrainerReview
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.dao.TrainerDao
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.dao.TrainerReviewDao
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.viewmodel.ReadTrainerViewModel


class ReadTrainerTab2Fragment : Fragment() {

    lateinit var fragmentReadTrainerTab2Binding: FragmentReadTrainerTab2Binding
    lateinit var readTrainerActivity: ReadTrainerActivity
    lateinit var readTrainerViewModel: ReadTrainerViewModel

    // 트레이너 리뷰 RecyclerView 구성을 위한 리스트
    var trainerReviewList = mutableListOf<TrainerReview>()

    // 전달 받을 게시글 Id
    var trainerPostId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadTrainerTab2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_trainer_tab2, container, false)
        readTrainerViewModel = ViewModelProvider(this).get(ReadTrainerViewModel::class.java)
        fragmentReadTrainerTab2Binding.readTrainerViewModel = readTrainerViewModel
        fragmentReadTrainerTab2Binding.lifecycleOwner = this

        readTrainerActivity = activity as ReadTrainerActivity

        // 전달 받은 arguments가 null 일경우, 0번 ID 반환 (0번은 오류 게시판)
        trainerPostId = arguments?.getInt("trainerPostId")?:0

        createReviewTrainer()
        settingChipType()
        settingTrainerPostData()
        gettingReviewData()
        settingRecyclerViewTrainerReview()

        return fragmentReadTrainerTab2Binding.root
    }

    /**
     * 함수 정리 (작성 순서)
     * 1. 리뷰 작성 페이지 이동
     * 2. chip 액션 설정
     * 3. 서버 데이터 보여주기
     * 4. RecyclerView (트레이너 리뷰) 설정
     * 5. Adapter와 ViewHolder 설정
     */

    fun createReviewTrainer(){
        fragmentReadTrainerTab2Binding.apply {
            inputReviewButton.apply {
                setOnClickListener {
                    val reviewInputIntent = Intent(activity, ReviewInputActivity::class.java)
                    startActivity(reviewInputIntent)
                }
            }
        }
    }

    private fun settingChipType(){
        fragmentReadTrainerTab2Binding.apply {
            chipReview1.apply {
                setOnClickListener {
                    val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)

                    val popup = PopupMenu(contextWrapper, this)
                    popup.inflate(R.menu.menu_trainer_review_chip)

                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.chipTrainerReView1 -> {
                                text = "최신순 ▼"

                            }
                            R.id.chipTrainerReView2 -> {
                                text = "별점순 ▼"
                            }
                        }
                        true
                    }
                    popup.show()
                }
            }
        }
    }

    fun settingTrainerPostData(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentReadTrainerTab2Binding.apply {
                val trainerPost = TrainerDao.selectTrainerPostData(trainerPostId)
                readTrainerViewModel?.apply {
                    readTrainerReviewAvg.value = trainerPost?.reviewAvg
                    readTrainerReviewCount.value = trainerPost?.reviewCount
                }
            }
        }
    }

    fun settingRecyclerViewTrainerReview(){
        fragmentReadTrainerTab2Binding.apply {
            recyclerViewTrainerReview.apply {
                adapter = TrainerReviewRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    fun gettingReviewData(){
        CoroutineScope(Dispatchers.Main).launch {
            trainerReviewList = TrainerReviewDao.gettingTrainerReviewList(trainerPostId)
            fragmentReadTrainerTab2Binding.recyclerViewTrainerReview.adapter?.notifyDataSetChanged()
        }
    }

    inner class TrainerReviewRecyclerViewAdapter: RecyclerView.Adapter<TrainerReviewRecyclerViewAdapter.TrainerReviewViewHolder>(){
        inner class TrainerReviewViewHolder(rowReadTrainerTab2Binding: RowReadTrainerTab2Binding): RecyclerView.ViewHolder(rowReadTrainerTab2Binding.root){
            val rowReadTrainerTab2Binding: RowReadTrainerTab2Binding

            init {
                this.rowReadTrainerTab2Binding = rowReadTrainerTab2Binding

                this.rowReadTrainerTab2Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerReviewViewHolder {
            val rowReadTrainerTab2Binding = RowReadTrainerTab2Binding.inflate(layoutInflater)
            val trainerReviewViewHolder = TrainerReviewViewHolder(rowReadTrainerTab2Binding)

            return trainerReviewViewHolder
        }

        override fun getItemCount(): Int {
            return trainerReviewList.size
        }

        override fun onBindViewHolder(holder: TrainerReviewViewHolder, position: Int) {
            holder.rowReadTrainerTab2Binding.userNickNameTextView.text = trainerReviewList[position].userId
            holder.rowReadTrainerTab2Binding.userReviewCreateDateTextView.text = trainerReviewList[position].createDate
            holder.rowReadTrainerTab2Binding.userReviewTextView.text = trainerReviewList[position].reviewText
            holder.rowReadTrainerTab2Binding.memberShipTypeTextView.text = trainerReviewList[position].membershipId

            CoroutineScope(Dispatchers.Main).launch {
                if(trainerReviewList[position].reviewImageUrls != null){
                    TrainerReviewDao.gettingTrainerReviewImage(readTrainerActivity, trainerReviewList[position].reviewImageUrls, holder.rowReadTrainerTab2Binding.userReviewImage)
                }
            }

            holder.rowReadTrainerTab2Binding.trainerNameTextView.text ="트레이너${position}"
            holder.rowReadTrainerTab2Binding.trainerReviewTextView.text = "안녕하세요. 트레이너${position} 코멘트"
            holder.rowReadTrainerTab2Binding.trainerReviewCreateDateTextView.text = "${position}달전"

        }
    }
}