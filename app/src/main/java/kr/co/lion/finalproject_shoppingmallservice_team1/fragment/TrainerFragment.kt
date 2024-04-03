package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.TRAINER_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTrainerBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowTrainerBinding


class TrainerFragment : Fragment() {

    lateinit var fragmentTrainerBinding: FragmentTrainerBinding
    lateinit var navigationActivity: NavigationActivity

    // 프래그먼트의 주소값을 담을 프로퍼티
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentTrainerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trainer, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbarTrainer()
        settingTabLayout()
        settingRecyclerViewTrainerHealth()

        return fragmentTrainerBinding.root
    }

    // 툴바 설정
    fun settingToolbarTrainer(){
        fragmentTrainerBinding.apply {
            toolbarTrainer.apply {
                inflateMenu(R.menu.menu_trainer)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.shopping -> {

                        }
                    }
                    true
                }
            }
        }
    }

    // tab 레이아웃 설정
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


    // RecyclerView 설정(헬스)
    fun settingRecyclerViewTrainerHealth(){
        fragmentTrainerBinding.apply {
            recyclerViewTrainer.apply {
                adapter = TrainerHealthRecyclerViewAdapter()
                layoutManager = GridLayoutManager(navigationActivity,2)
            }
        }
    }

    // RecyclerView (헬스)
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
                this.rowTrainerBinding.apply {
                    trainerProfileImageView.setOnClickListener {
                        replaceFragment(TRAINER_FRAGMENT_NAME.READ_TRAINER_FRAGMENT, true, true, null)
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


    // RecyclerView 설정(필라테스)
    fun settingRecyclerViewTrainerPilatest(){
        fragmentTrainerBinding.apply {
            recyclerViewTrainer.apply {
                adapter = TrainerPilatestRecyclerViewAdapter()
                layoutManager = GridLayoutManager(navigationActivity,2)
            }
        }
    }

    // RecyclerView(필라테스)
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
                this.rowTrainerBinding.apply {
                    trainerProfileImageView.setOnClickListener {
                        Log.d("test1234","123")
                        replaceFragment(TRAINER_FRAGMENT_NAME.READ_TRAINER_FRAGMENT, true, true, null)
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

    fun replaceFragment(name: TRAINER_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            TRAINER_FRAGMENT_NAME.READ_TRAINER_FRAGMENT -> {
                newFragment = ReadTrainerFragment()
            }
            TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB1_FRAGMENT -> {
                newFragment = ReadTrainerTab1Fragment()
            }
            TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB2_FRAGMENT -> {
                newFragment = ReadTrainerTab2Fragment()
            }
            TRAINER_FRAGMENT_NAME.READ_TRAINER_TAB3_FRAGMENT -> {
                newFragment = ReadTrainerTab3Fragment()
            }
        }

        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){
            if(isAnimate == true){

                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }
            fragmentTransaction.replace(R.id.fragmentTrainer, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }
}