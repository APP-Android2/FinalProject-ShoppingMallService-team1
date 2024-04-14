package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.finalproject_shoppingmallservice_team1.CENTER_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.ReadTrainerActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.TRAINER_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCenterBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerBinding

class CenterFragment : Fragment() {

    lateinit var fragmentCenterBinding:FragmentCenterBinding

    // 프래그먼트 객체를 담을 변수
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_center, container, false)

        val purpleColor = Color.parseColor("#800080") // 보라색 코드
        val defaultColor = ColorStateList.valueOf(Color.TRANSPARENT) // 기본 색상을 설정

        // 각 Chip을 찾고 클릭 리스너 설정
        val chipDistance = view.findViewById<Chip>(R.id.chip_distance)
        val chipDailyPass = view.findViewById<Chip>(R.id.chip_daily_pass)
        val chipDiscount = view.findViewById<Chip>(R.id.chip_discount)

        val chipClickListener = View.OnClickListener { v ->
            val chip = v as Chip
            // 현재 칩 색상 확인 후 색상 변경
            if (chip.chipBackgroundColor?.defaultColor != purpleColor) {
                chip.chipBackgroundColor = ColorStateList.valueOf(purpleColor)
            } else {
                chip.chipBackgroundColor = defaultColor
            }
        }

        chipDistance.setOnClickListener(chipClickListener)
        chipDailyPass.setOnClickListener(chipClickListener)
        chipDiscount.setOnClickListener(chipClickListener)

        settingToolbarCenterFragment()
        onOffsetChanged()
        settingTabLayout()

        return inflater.inflate(R.layout.fragment_center, container, false)
    }

    fun settingTabLayout(){
        fragmentCenterBinding.apply{
            trainerInfoTab.apply {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 탭이 선택되었을 때 호출되는 메서드
                        val position = tab?.position

                        when(position){
                            0 -> {
                                replaceFragment(CENTER_FRAGMENT_NAME.CENTER_FRAGMENT,false, false, null)
                            }
                            1 -> {
                                replaceFragment(CENTER_FRAGMENT_NAME.CENTER_SWIMMING_SELECT, false, false, null)
                            }
                            2 -> {
                                replaceFragment(CENTER_FRAGMENT_NAME.CENTER_PILATES_SELECT, false, false, null)
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

    private fun onOffsetChanged() {
        TODO("Not yet implemented")
    }

    private fun settingToolbarCenterFragment() {
        TODO("Not yet implemented")
    }

    fun replaceFragment(name: CENTER_FRAGMENT_NAME, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        SystemClock.sleep(100)

        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            CENTER_FRAGMENT_NAME.CENTER_FRAGMENT -> {
                newFragment = CenterFragment()
            }
            CENTER_FRAGMENT_NAME.CENTER_PILATES_SELECT -> {

            }
            CENTER_FRAGMENT_NAME.CENTER_SWIMMING_SELECT -> {
            }
        }

        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){

            // 애니메이션 설정
            if(isAnimate == true){

                if(oldFragment != null){
                    // old에서 new가 새롭게 보여질 때 old의 애니메이션
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    // new에서 old로 되돌아갈때 old의 애니메이션
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }

                // old에서 new가 새롭게 보여질 때 new의 애니메이션
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                // new에서 old로 되돌아갈때 new의 애니메이션
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }

            fragmentTransaction.replace(R.id.readTrainerTabView, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }
            fragmentTransaction.commit()
        }
    }
}


