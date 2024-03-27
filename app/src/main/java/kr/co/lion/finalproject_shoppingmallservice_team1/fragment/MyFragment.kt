package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyBinding

class MyFragment : Fragment() {

    lateinit var fragmentMyBinding: FragmentMyBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyBinding =DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)

        navigationActivity = activity as NavigationActivity

        settingToolbarMy()

        return fragmentMyBinding.root
    }

    // Toolbar 설정
    fun settingToolbarMy(){
        fragmentMyBinding.apply {
            toolbarMy.apply {
                // 타이틀
                title = "마이페이지"
                // 메뉴
                inflateMenu(R.menu.menu_my)
            }
        }
    }
}