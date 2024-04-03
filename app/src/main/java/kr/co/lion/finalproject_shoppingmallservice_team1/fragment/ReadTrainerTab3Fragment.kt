package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadTrainerTab3Binding

class ReadTrainerTab3Fragment : Fragment() {

    lateinit var fragmentReadTrainerTab3Binding: FragmentReadTrainerTab3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadTrainerTab3Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_trainer_tab3, container, false)
        fragmentReadTrainerTab3Binding.lifecycleOwner = this



        return fragmentReadTrainerTab3Binding.root
    }


}