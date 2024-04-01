package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_BOTTOM_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.HOME_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeAddressBottomBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentHomeAddressBottomMapBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowAddressBottomBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.viewmodel.HomeAddressBottomViewModel

class HomeAddressBottomFragment : BottomSheetDialogFragment() {

    lateinit var fragmentHomeAddressBottomBinding: FragmentHomeAddressBottomBinding
    lateinit var navigationActivity: NavigationActivity
    lateinit var homeAddressBottomViewModel: HomeAddressBottomViewModel

    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeAddressBottomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_address_bottom, container, false)
        homeAddressBottomViewModel = HomeAddressBottomViewModel()
        fragmentHomeAddressBottomBinding.homeAddressBottomViewModel = homeAddressBottomViewModel
        fragmentHomeAddressBottomBinding.lifecycleOwner = this

        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingRecyclerViewAddressMain()
        settingEvent()

        return fragmentHomeAddressBottomBinding.root
    }

    fun settingToolbar(){
        fragmentHomeAddressBottomBinding.apply {
            toolbarAddressMain.apply {
                inflateMenu(R.menu.home_address_menu)
            }
        }
    }

    fun settingEvent(){
        fragmentHomeAddressBottomBinding.apply {
            textViewHomeBottomNowAddress.apply {
                setOnClickListener {
                    replaceFragment(HOME_BOTTOM_FRAGMENT_NAME.HOME_ADDRESS_BOTTOM_MAP_FRAGMENT, true, true, null)
                }
            }
        }
    }

    fun settingRecyclerViewAddressMain(){
        fragmentHomeAddressBottomBinding.apply {
            recyclerViewHomeBottomAddress.apply {
                adapter = BottomRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(navigationActivity)

                val deco = MaterialDividerItemDecoration(navigationActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    inner class BottomRecyclerViewAdapter : RecyclerView.Adapter<BottomRecyclerViewAdapter.BottomViewHoleder>(){
        inner class BottomViewHoleder(rowAddressBottomBinding: RowAddressBottomBinding):RecyclerView.ViewHolder(rowAddressBottomBinding.root){
            val rowAddressBottomBinding : RowAddressBottomBinding

            init {
                this.rowAddressBottomBinding = rowAddressBottomBinding
                rowAddressBottomBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHoleder {
            val rowAddressBottomBinding = RowAddressBottomBinding.inflate(layoutInflater)
            val bottomViewHoleder = BottomViewHoleder(rowAddressBottomBinding)
            return bottomViewHoleder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: BottomViewHoleder, position: Int) {
            holder.rowAddressBottomBinding.textViewHomeAddress.text = "서울시 중랑구 00동 00아파트"
        }
    }

    fun replaceFragment(name: HOME_BOTTOM_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(200)

        val fragmentTransaction = childFragmentManager.beginTransaction().setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){

            HOME_BOTTOM_FRAGMENT_NAME.HOME_ADDRESS_BOTTOM_MAP_FRAGMENT -> {
                newFragment = HomeAddressBottomMapFragment()
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

            fragmentTransaction.replace(R.id.frameHomeAddress, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }

    // 다이얼로그가 만들어질 때 자동으로 호출되는 메서드
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 다이얼로그를 받는다.
        val dialog = super.onCreateDialog(savedInstanceState)
        // 다이얼로그가 보일때 동작하는 리스너
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            // 높이를 설정한다.
            setBottomSheetHeight(bottomSheetDialog)
        }
        return dialog
    }

    // BottomSheet의 높이를 설정해준다.
    fun setBottomSheetHeight(bottomSheetDialog: BottomSheetDialog){
        // BottomSheet의 기본 뷰 객체를 가져온다
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
        // BottomSheet 높이를 설정할 수 있는 객체를 생성한다.
        val behavior = BottomSheetBehavior.from(bottomSheet)
        // 높이를 설정한다.
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // BottomSheet의 높이를 구한다(화면 액정의 85% 크기)
    fun getBottomSheetDialogHeight() : Int {
        return (getWindowHeight() * 0.9).toInt()
    }

    // 사용자 단말기 액정의 길이를 구해 반환하는 메서드
    fun getWindowHeight() : Int {
        // 화면 크기 정보를 담을 배열 객체
        val displayMetrics = DisplayMetrics()
        // 액정의 가로 세로 길이 정보를 담아준다.
        navigationActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // 세로길이를 반환해준다.
        return displayMetrics.heightPixels
    }
}