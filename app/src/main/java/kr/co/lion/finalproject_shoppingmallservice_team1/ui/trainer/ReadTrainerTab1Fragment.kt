package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerTab1Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowReadTrainerImageBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowReadTrainerMembershipBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.dao.TrainerDao
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.viewmodel.ReadTrainerViewModel


class ReadTrainerTab1Fragment : Fragment() {

    lateinit var fragmentReadTrainerTab1Binding: FragmentReadTrainerTab1Binding
    lateinit var readTrainerViewModel: ReadTrainerViewModel

    // 전달 받을 게시글 Id
    var trainerPostId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadTrainerTab1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_trainer_tab1, container, false)
        readTrainerViewModel = ViewModelProvider(this).get(ReadTrainerViewModel::class.java)
        fragmentReadTrainerTab1Binding.readTrainerViewModel = readTrainerViewModel
        fragmentReadTrainerTab1Binding.lifecycleOwner = this

        // 전달 받은 arguments가 null 일경우, 0번 ID 반환 (0번은 오류 게시판)
        trainerPostId = arguments?.getInt("trainerPostId")?:0

        settingRecyclerViewTrainerTab1()
        settingTrainerPostData()

        return fragmentReadTrainerTab1Binding.root
    }

    /**
     * 함수 정리 (작성 순서)
     * 1. RecyclerView 설정 (settingRecyclerViewTrainerTab1())
     * 2. 서버 데이터 보여주기 (settingTrainerPostData())
     */

    fun settingRecyclerViewTrainerTab1(){
        fragmentReadTrainerTab1Binding.apply {
            recyclerViewTrainerPt.apply {
                val membershipData = listOf("10회", "15회", "20회", "25회") // 예시 데이터

                adapter = TrainerMembershipAdapter(membershipData)
                layoutManager = LinearLayoutManager(context)
            }
            recyclerViewTrainerImage.apply {
                val imageList = listOf(R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background, R.drawable.fitmoa_logo_background)

                adapter = TrainerImageAdapter(imageList)
                layoutManager = GridLayoutManager(context,3)
            }
        }
    }

    fun settingTrainerPostData(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentReadTrainerTab1Binding.apply {
                val trainerPost = TrainerDao.selectTrainerPostData(trainerPostId)
                readTrainerViewModel?.apply {
                    readTrainerNotificationText.value = trainerPost?.notificationText
                    readTrainerAboutMeText.value = trainerPost?.aboutMeText
                    readTrainerMemberShipText.value = trainerPost?.memberShipText
                    readTrainerCareerText.value = trainerPost?.careerText
                    readTrainerOrgNameTextView.value = trainerPost?.centerName
                    readTrainerLocationTextView.value = trainerPost?.centerLocation
                }
            }
        }
    }
}


// 회원권 RecyclerView
class TrainerMembershipAdapter(val dataList: List<String>) : RecyclerView.Adapter<TrainerMembershipViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerMembershipViewHolder {
        // 뷰 홀더를 만들어준다.
        val inflater = LayoutInflater.from(parent.context)
        val rowReadTrainerMembershipBinding = RowReadTrainerMembershipBinding.inflate(inflater, parent, false)

        return TrainerMembershipViewHolder(rowReadTrainerMembershipBinding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TrainerMembershipViewHolder, position: Int) {
        holder.holderBind(dataList[position])
    }
}

// 회원권 ViewHolder
class TrainerMembershipViewHolder(private val rowReadTrainerMembershipBinding: RowReadTrainerMembershipBinding) : RecyclerView.ViewHolder(rowReadTrainerMembershipBinding.root) {
    fun holderBind(data: String) {
        rowReadTrainerMembershipBinding.textViewMembershipCount.text = data
        rowReadTrainerMembershipBinding.textViewMembershipMoney.text = "${data}원"
        rowReadTrainerMembershipBinding.textViewMembershipTotalMoney.text = "총 결제금액${data}원"
    }
}



// 트레이너 사진 RecyclerView 설정
class TrainerImageAdapter(val imageList: List<Int>): RecyclerView.Adapter<TrainerImageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerImageViewHolder {
        // 뷰 홀더를 만들어준다.
        val inflater = LayoutInflater.from(parent.context)
        val rowReadTrainerImageBinding = RowReadTrainerImageBinding.inflate(inflater, parent, false)

        return TrainerImageViewHolder(rowReadTrainerImageBinding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: TrainerImageViewHolder, position: Int) {
        holder.holderBind(imageList[position])
    }
}

// 트레이너 사진 ViewHolder
class TrainerImageViewHolder(private val rowReadTrainerImageBinding: RowReadTrainerImageBinding): RecyclerView.ViewHolder(rowReadTrainerImageBinding.root){

    fun holderBind(imageTest: Int){
        rowReadTrainerImageBinding.trainerDailyImageView.setImageResource(imageTest)
    }
}
