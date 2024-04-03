package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.ReviewInputActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerTab2Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowReadTrainerTab2Binding


class ReadTrainerTab2Fragment : Fragment() {

    lateinit var fragmentReadTrainerTab2Binding: FragmentReadTrainerTab2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadTrainerTab2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_trainer_tab2, container, false)
        fragmentReadTrainerTab2Binding.lifecycleOwner = this

        createReviewTrainer()
        settingRecyclerViewTrainerReview()

        return fragmentReadTrainerTab2Binding.root
    }


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


    // 트레이너 리뷰_RecyclerView 설정
    fun settingRecyclerViewTrainerReview(){
        fragmentReadTrainerTab2Binding.apply {
            recyclerViewTrainerReview.apply {
                adapter = TrainerReviewRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    // 리사이클러 뷰 (리뷰)
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
            return 5
        }

        override fun onBindViewHolder(holder: TrainerReviewViewHolder, position: Int) {
            holder.rowReadTrainerTab2Binding.userNickNameTextView.text = "닉네임${position}"
            holder.rowReadTrainerTab2Binding.userReviewCreateDateTextView.text = "${position}달전"
            holder.rowReadTrainerTab2Binding.userReviewTextView.text = "구체적인 수업 굿${position}"
            holder.rowReadTrainerTab2Binding.memberShipTypeTextView.text = "PT ${position}0회"

            holder.rowReadTrainerTab2Binding.trainerNameTextView.text ="트레이너${position}"
            holder.rowReadTrainerTab2Binding.trainerReviewTextView.text = "안녕하세요. 트레이너${position} 코멘트"
            holder.rowReadTrainerTab2Binding.trainerReviewCreateDateTextView.text = "${position}달전"

        }
    }

}