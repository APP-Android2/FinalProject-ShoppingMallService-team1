package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.finalproject_shoppingmallservice_team1.MY_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NAVIGATION_FRAGMENT_NAME
import kr.co.lion.finalproject_shoppingmallservice_team1.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyNotificationBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyNotificationBinding

class MyNotificationFragment : Fragment() {

    lateinit var fragmentMyNotificationBinding: FragmentMyNotificationBinding
    lateinit var navigationActivity: NavigationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyNotificationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_notification, container, false)
        navigationActivity = activity as NavigationActivity

        settingToolbar()
        settingRecyclerViewMyNotification()

        return fragmentMyNotificationBinding.root
    }

    // Toolbar 설정
    fun settingToolbar(){
        fragmentMyNotificationBinding.apply {
            toolbarMyNotification.apply {
                // 타이틀
                title = "공지 / 이벤트"
            }
        }
    }

    // RecyclerView 설정
    fun settingRecyclerViewMyNotification(){
        fragmentMyNotificationBinding.apply {
            recyclerViewMyNotification.apply {
                // 어댑터
                adapter = MyNotificationRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(navigationActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(navigationActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class MyNotificationRecyclerViewAdapter : RecyclerView.Adapter<MyNotificationRecyclerViewAdapter.MyNotificationViewHolder>() {
        // ViewHolder
        inner class MyNotificationViewHolder(rowMyNotificationBinding: RowMyNotificationBinding) : RecyclerView.ViewHolder(rowMyNotificationBinding.root){
            val rowMyNotificationBinding: RowMyNotificationBinding

            init {
                this.rowMyNotificationBinding = rowMyNotificationBinding

                this.rowMyNotificationBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNotificationViewHolder {
            val rowMyNotificationBinding = RowMyNotificationBinding.inflate(layoutInflater)
            val myNotificationViewHolder = MyNotificationViewHolder(rowMyNotificationBinding)

            return myNotificationViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: MyNotificationViewHolder, position: Int) {
            holder.rowMyNotificationBinding.textViewRowMyNotificationTitle.text = "새로운 공지사항 $position"
            holder.rowMyNotificationBinding.textViewRowMyNotificationDate.text = "${position}일 전"
        }
    }
}