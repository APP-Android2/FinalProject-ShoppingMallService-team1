package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentInfoCenterMainBinding

class InfoCenterFragment : Fragment() {
    private lateinit var binding: FragmentInfoCenterMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoCenterMainBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments?.let {
            val centerName = it.getString("centerName")
            binding.ReadTrainerNameTextView.text = centerName  // 예시로 이름을 설정
        }

        return view
    }
}
