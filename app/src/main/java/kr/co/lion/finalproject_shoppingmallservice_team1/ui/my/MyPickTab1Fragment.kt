package kr.co.lion.finalproject_shoppingmallservice_team1.ui.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.finalproject_shoppingmallservice_team1.ui.home.NavigationActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentMyPickTab1Binding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowMyPickTab1Binding

class MyPickTab1Fragment : Fragment() {

    lateinit var fragmentMyPickTab1Binding: FragmentMyPickTab1Binding
    lateinit var navigationActivity: NavigationActivity

    var isImageClick = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMyPickTab1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_pick_tab1, container, false)
        navigationActivity = activity as NavigationActivity

        settingRecyclerViewMyPickTab1()

        return fragmentMyPickTab1Binding.root
    }

    // RecyclerView 설정
    fun settingRecyclerViewMyPickTab1(){
        fragmentMyPickTab1Binding.apply {
            recyclerViewMyPickTab1.apply {
                // 어댑터
                adapter = MyPickTab1RecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = GridLayoutManager(navigationActivity, 2)
            }
        }
    }

    // RecyclerView 의 어댑터
    inner class MyPickTab1RecyclerViewAdapter : RecyclerView.Adapter<MyPickTab1RecyclerViewAdapter.MyPickTab1ViewHolder>() {
        // ViewHolder
        inner class MyPickTab1ViewHolder(rowMyPickTab1Binding: RowMyPickTab1Binding) : RecyclerView.ViewHolder(rowMyPickTab1Binding.root){
            val rowMyPickTab1Binding: RowMyPickTab1Binding

            init {
                this.rowMyPickTab1Binding = rowMyPickTab1Binding

                this.rowMyPickTab1Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )

                // 찜 버튼 설정
                this.rowMyPickTab1Binding.buttonMyPickTab1.setOnClickListener {
                    isImageClick = !isImageClick
                    updateImageButton()
                }
            }

            // 찜 버튼 클릭에 따른 이미지 변경
            fun updateImageButton(){
                rowMyPickTab1Binding.apply {
                    if(isImageClick){
                        buttonMyPickTab1.setImageResource(R.drawable.favorite_fill)
                    } else {
                        buttonMyPickTab1.setImageResource(R.drawable.favorite)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPickTab1ViewHolder {
            val rowMyPickTab1Binding = RowMyPickTab1Binding.inflate(layoutInflater)
            val myPickTab1ViewHolder = MyPickTab1ViewHolder(rowMyPickTab1Binding)

            return myPickTab1ViewHolder
        }

        override fun getItemCount(): Int {
            return 9
        }

        override fun onBindViewHolder(holder: MyPickTab1ViewHolder, position: Int) {
            holder.rowMyPickTab1Binding.tvRowMyPickTrainerName.text = "트레이너 이름 $position"
        }
    }
}