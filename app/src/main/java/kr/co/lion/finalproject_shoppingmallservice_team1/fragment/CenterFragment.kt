package kr.co.lion.finalproject_shoppingmallservice_team1.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.tabs.TabLayout
import kr.co.lion.finalproject_shoppingmallservice_team1.AlarmActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.ChatActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.R
import kr.co.lion.finalproject_shoppingmallservice_team1.ShoppingCartActivity
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.FragmentCenterBinding
import kr.co.lion.finalproject_shoppingmallservice_team1.databinding.RowCenterListBinding

class CenterFragment : Fragment() {

    lateinit var fragmentCenterBinding: FragmentCenterBinding
    lateinit var recyclerView: RecyclerView


    val centers = listOf(
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
        CenterGym("A센터", "정기 휴무 OO일, 토요일 4시까지 영업 등", "헬스", "서울특별시 강남구 ", R.drawable.ic_launcher_background),
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCenterBinding = FragmentCenterBinding.inflate(inflater, container, false)
        val view = fragmentCenterBinding.root


        // Chip 색상 변경 로직 유지
        val purpleColor = Color.parseColor("#800080")
        val defaultColor = ColorStateList.valueOf(Color.TRANSPARENT)
        val chipClickListener = View.OnClickListener { v ->
            val chip = v as Chip
            if (chip.chipBackgroundColor?.defaultColor != purpleColor) {
                chip.chipBackgroundColor = ColorStateList.valueOf(purpleColor)
            } else {
                chip.chipBackgroundColor = defaultColor
            }
        }

        fragmentCenterBinding.chipDistance.setOnClickListener(chipClickListener)
        fragmentCenterBinding.chipDailyPass.setOnClickListener(chipClickListener)
        fragmentCenterBinding.chipDiscount.setOnClickListener(chipClickListener)
        settingToolbar()
        setupRecyclerView()
        settingChipType()



        return view

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCenterBinding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> showHealth()
                    1 -> replaceFragmentWith(SelectSwimmingFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // Not needed for this implementation
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Not needed for this implementation
            }
        })
    }

    private fun showHealth() {
        setupRecyclerView()
    }
    fun settingToolbar() {
        fragmentCenterBinding.apply {
            toolbar.apply {
                // 메뉴 인플레이션
                inflateMenu(R.menu.center_menu)

                // 메뉴 아이템 클릭 리스너 설정
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menuCenterShopping -> {
                            // 쇼핑카트 아이콘 클릭 시 ShoppingCartActivity로 이동
                            val intent = Intent(context, ShoppingCartActivity::class.java)
                            startActivity(intent)
                            true
                        }
                        else -> false // 다른 메뉴 아이템에 대한 처리가 필요한 경우
                    }
                }
            }
        }
    }

    private fun settingChipType(){
        fragmentCenterBinding.apply {
            chipDistance.apply {
                setOnClickListener {
                    val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)

                    val popup = PopupMenu(contextWrapper, this)
                    popup.inflate(R.menu.menu_center_chip)

                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.chipCenterDistance -> {
                                text = "거리순"

                            }
                            R.id.chipCenterPopular-> {
                                text = "인기순"
                            }
                        }
                        true
                    }
                    popup.show()
                }
            }
        }
    }
    private fun setupRecyclerView() {
        fragmentCenterBinding.gymListRecylcler.apply {
            adapter = CenterAdapter(centers, object : CenterAdapter.OnItemClickListener {
                override fun onItemClick(center:CenterGym) {
                    val fragment = InfoCenterFragment().apply {
                        arguments = Bundle().apply {
                            putString("centerName", center.name)
                        }
                    }
                    replaceFragment(fragment)
                }
            })
            layoutManager = LinearLayoutManager(context)
            val deco = MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL)
            addItemDecoration(deco)
        }
    }
    private fun replaceFragmentWith(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_home, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_center, fragment)
            .addToBackStack(null)
            .commit()
    }


    class CenterAdapter(private val centerList: List<CenterGym>, private val listener: OnItemClickListener) : RecyclerView.Adapter<CenterAdapter.CenterViewHolder>() {
        interface OnItemClickListener {
            fun onItemClick(center: CenterGym)
        }

        class CenterViewHolder(val binding: RowCenterListBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
            val binding = RowCenterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CenterViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
            val center = centerList[position]
            holder.binding.imageViewCenterPicture.setImageResource(center.imageResourceId)
            holder.binding.textViewNotice.text = center.notice
            holder.binding.textViewCategory.text = center.category
            holder.binding.textViewCenterName.text = center.name
            holder.binding.textViewDistance.text = center.address
        }



        override fun getItemCount() = centerList.size
    }
}

data class CenterGym(
    val name: String,
    val notice: String,
    val category: String,
    val address: String,
    val imageResourceId: Int
)