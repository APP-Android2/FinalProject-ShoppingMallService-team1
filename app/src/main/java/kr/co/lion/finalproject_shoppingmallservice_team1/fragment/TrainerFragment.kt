package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentTrainerBinding


class TrainerFragment : Fragment() {

    lateinit var fragmentTrainerBinding: FragmentTrainerBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentTrainerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trainer, container, false)

        navigationActivity = activity as NavigationActivity


        settingToolbarTrainer()

        return fragmentTrainerBinding.root
    }

    // 툴바 설정
    fun settingToolbarTrainer(){
        fragmentTrainerBinding.apply {
            toolbarTrainer.apply {
                //title = "트레이너"
                inflateMenu(R.menu.menu_trainer)
            }
        }
    }
}