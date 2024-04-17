package kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer

import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.shoppingcart.ShoppingCartActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTrainerBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowTrainerBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.trainer.viewmodel.TrainerViewModel


class TrainerFragment : Fragment() {

    lateinit var fragmentTrainerBinding: FragmentTrainerBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var trainerViewModel: TrainerViewModel

    var isImageClick = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentTrainerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trainer, container, false)
        navigationActivity = activity as NavigationActivity
        trainerViewModel = TrainerViewModel()
        fragmentTrainerBinding.trainerViewModel = trainerViewModel
        fragmentTrainerBinding.lifecycleOwner = this@TrainerFragment

        settingToolbarTrainer()
        settingTabLayout()
        settingRecyclerViewTrainerHealth()
        settingChip1()

        return fragmentTrainerBinding.root
    }

    /**
     * 함수 정리 (작성 순서)
     * 1. 툴바 설정 (settingToolbarTrainer())
     * 2. Tab 레이아웃 설정 (settingTabLayout())
     * 3. chip 목록 설정 (settingChip1())
     * 4. RecyclerView 설정(헬스) (settingRecyclerViewTrainerHealth())
     * 5. 헬스 항목의 Adapter와 ViewHolder 설정 (찜 기능 추가)
     * 6. RecyclerView 설정(필라테스) (settingRecyclerViewTrainerPilatest())
     * 7. 필라테스 항목의 Adapter와 ViewHolder 설정 (찜 기능 추가)
     */


    fun settingToolbarTrainer(){
        fragmentTrainerBinding.apply {
            toolbarTrainer.apply {
                inflateMenu(R.menu.menu_trainer)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemTrainerShopping -> {
                            val shoppingCartIntent = Intent(navigationActivity, ShoppingCartActivity::class.java)
                            startActivity(shoppingCartIntent)
                        }
                    }
                    true
                }
            }
        }
    }

    fun settingTabLayout(){
        fragmentTrainerBinding.apply {
            trainerTab.apply {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 탭이 선택되었을 때 호출되는 메서드
                        val position = tab?.position

                        when(position){
                            0 -> {
                                settingRecyclerViewTrainerHealth()
                            }
                            1 -> {
                                settingRecyclerViewTrainerPilatest()
                            }
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        // 선택이 해제된 탭의 경우 처리할 내용

                    }
                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        // 이미 선택된 탭이 다시 선택된 경우 처리할 내용
                    }
                })
            }
        }
    }

    private fun settingChip1(){
        fragmentTrainerBinding.apply {
            trainerMainChip1.apply {
                setOnClickListener {
                    val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)

                    val popup = PopupMenu(contextWrapper, this)
                    popup.inflate(R.menu.menu_trainer_main_chip)

                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.chipTrainer1 -> {
                                text = "인기순"
                            }
                            R.id.chipTrainer2 -> {
                                text = "거리순"
                            }
                        }
                        true
                    }
                    popup.show()
                }
            }
        }
    }

    fun settingRecyclerViewTrainerHealth(){
        fragmentTrainerBinding.apply {
            recyclerViewTrainer.apply {
                adapter = TrainerHealthRecyclerViewAdapter()
                layoutManager = GridLayoutManager(navigationActivity,2)
            }
        }
    }

    inner class TrainerHealthRecyclerViewAdapter: RecyclerView.Adapter<TrainerHealthRecyclerViewAdapter.TrainerHealthViewHolder>(){
        inner class TrainerHealthViewHolder(rowTrainerBinding: RowTrainerBinding): RecyclerView.ViewHolder(rowTrainerBinding.root){
            val rowTrainerBinding:RowTrainerBinding

            init {
                this.rowTrainerBinding = rowTrainerBinding

                this.rowTrainerBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // 이미지 클릭 시 상세페이지 이동 설정
                // 찜 버튼 클릭 설정
                this.rowTrainerBinding.apply {
                    cardViewTrainer.setOnClickListener {
                        navigationActivity.readTrainerRequest()
                    }
                    trainerMyPickImageButton.setOnClickListener {
                        // 추후 DB 컬럼 값으로 변경 되도록 하기. (현재 단일 체크 가능)
                        isImageClick = !isImageClick
                        updateImageButton()
                    }
                }
            }

            // 찜 버튼 클릭에 따른 이미지 변경
            fun  updateImageButton(){
                rowTrainerBinding.apply {
                    if(isImageClick){
                        trainerMyPickImageButton.setImageResource(R.drawable.favorite_fill)
                        Toast.makeText(navigationActivity, "'찜' 선택 되었습니다.", Toast.LENGTH_SHORT).show()
                    } else{
                        trainerMyPickImageButton.setImageResource(R.drawable.favorite)
                        Toast.makeText(navigationActivity, "'찜' 해지 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerHealthViewHolder {
            val rowTrainerBinding = RowTrainerBinding.inflate(layoutInflater)
            val trainerHelthViewHolder = TrainerHealthViewHolder(rowTrainerBinding)

            return trainerHelthViewHolder
        }

        override fun getItemCount(): Int {
            return 9
        }

        override fun onBindViewHolder(holder: TrainerHealthViewHolder, position: Int) {
            holder.rowTrainerBinding.healthTrainerNameTextView.text = "이름${position}"
            holder.rowTrainerBinding.healthTrainerOrgNameTextView.text = "헬스 센터${position}"
            holder.rowTrainerBinding.healthTrainerAddressTextView.text = "주소${position}"
            holder.rowTrainerBinding.textViewType.text = "헬스타입"
        }
    }



    fun settingRecyclerViewTrainerPilatest(){
        fragmentTrainerBinding.apply {
            recyclerViewTrainer.apply {
                adapter = TrainerPilatestRecyclerViewAdapter()
                layoutManager = GridLayoutManager(navigationActivity,2)
            }
        }
    }

    inner class TrainerPilatestRecyclerViewAdapter: RecyclerView.Adapter<TrainerPilatestRecyclerViewAdapter.TrainerPilatestViewHolder>(){
        inner class TrainerPilatestViewHolder(rowTrainerBinding: RowTrainerBinding): RecyclerView.ViewHolder(rowTrainerBinding.root){
            val rowTrainerBinding:RowTrainerBinding

            init {
                this.rowTrainerBinding = rowTrainerBinding

                this.rowTrainerBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // 이미지 클릭 시 상세페이지 이동 설정
                // 찜 버튼 클릭 설정
                this.rowTrainerBinding.apply {
                    cardViewTrainer.setOnClickListener {
                        navigationActivity.readTrainerRequest()
                    }
                    trainerMyPickImageButton.setOnClickListener {
                        // 추후 DB 컬럼 값으로 변경 되도록 하기. (현재 단일 체크 가능)
                        isImageClick = !isImageClick
                        updateImageButton()
                    }
                }
            }

            // 찜 버튼 클릭에 따른 이미지 변경
            fun  updateImageButton(){
                rowTrainerBinding.apply {
                    if(isImageClick){
                        trainerMyPickImageButton.setImageResource(R.drawable.favorite_fill)
                        Toast.makeText(navigationActivity, "'찜' 선택 되었습니다.", Toast.LENGTH_SHORT).show()
                    } else{
                        trainerMyPickImageButton.setImageResource(R.drawable.favorite)
                        Toast.makeText(navigationActivity, "'찜' 해지 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerPilatestViewHolder {
            val rowTrainerBinding = RowTrainerBinding.inflate(layoutInflater)
            val trainerPilatestViewHolder = TrainerPilatestViewHolder(rowTrainerBinding)

            return trainerPilatestViewHolder
        }

        override fun getItemCount(): Int {
            return 9
        }

        override fun onBindViewHolder(holder: TrainerPilatestViewHolder, position: Int) {
            holder.rowTrainerBinding.healthTrainerNameTextView.text = "이름${position}"
            holder.rowTrainerBinding.healthTrainerOrgNameTextView.text = "필라테스 센터${position}"
            holder.rowTrainerBinding.healthTrainerAddressTextView.text = "주소${position}"
            holder.rowTrainerBinding.textViewType.text = "필라타입"
        }
    }
}