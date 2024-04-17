package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCenterBinding

class CenterFragment : Fragment() {

    lateinit var fragmentCenterBinding: FragmentCenterBinding

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


        return inflater.inflate(R.layout.fragment_center, container, false)
    }

}

