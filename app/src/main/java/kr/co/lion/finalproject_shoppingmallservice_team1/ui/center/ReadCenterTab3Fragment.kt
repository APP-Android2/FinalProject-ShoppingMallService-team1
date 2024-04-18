package kr.co.lion.finalproject_shoppingmallservice_team1.ui.center

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentReadCenterTab3Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.visitconsulting.ConsultingActivity

class ReadCenterTab3Fragment : Fragment() {

    lateinit var fragmentReadCenterTab3Binding: FragmentReadCenterTab3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentReadCenterTab3Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_center_tab3, container, false)
        fragmentReadCenterTab3Binding.lifecycleOwner = this

        settingButtonClick()

        return fragmentReadCenterTab3Binding.root
    }

    fun settingButtonClick(){
        fragmentReadCenterTab3Binding.apply {
            // 방문상담 이동 설정
            buttonConsulting.apply {
                setOnClickListener {
                    val consultingIntent = Intent(activity, ConsultingActivity::class.java)
                    startActivity(consultingIntent)
                }
            }
            // 채팅상담 이동 설정
            buttonChatting.apply {
                setOnClickListener {

                }
            }
        }
    }

}